#!/usr/bin/env python3

import re
import sys
from pathlib import Path
from subprocess import check_output, check_call, DEVNULL
from time import sleep

import gpio

BAUDRATE = 1000000
FLASH_START = 0x08000000
STM32FLASH = "/usr/bin/stm32flash"
RESET_GPIO = 6 * 32 + 8  # PG8


def stm32_crc(buf: bytes) -> int:
    """
    STM32 compatible CRC implementation.
    Inspired by stm32flash source code.
    """
    crcpoly_be = 0x04C11DB7
    crc_msbmask = 0x80000000
    crc_init_value = 0xFFFFFFFF

    if len(buf) % 4 != 0:
        raise ValueError("Buffer len must be multiple of 4")

    result = crc_init_value

    for i in range(0, len(buf), 4):
        data = int.from_bytes(buf[i : i + 4], "little", signed=False)

        result ^= data

        for _ in range(32):
            if result & crc_msbmask:
                result = (result << 1) ^ crcpoly_be
            else:
                result = result << 1
        result &= 0xFFFFFFFF

    return result


def get_mcu_crc(length: int, uart: Path) -> int:
    out = check_output(
        [STM32FLASH, "-R", "-b", f"{BAUDRATE}", "-C", "-S", f"{FLASH_START:#02x}:{length}", str(uart)],
        encoding="utf-8",
    )
    result = re.findall(r" = (0x[0-9a-f]*)", out)[0]
    return int(result, 16)


def check_crc_match(firmware: Path, uart: Path) -> bool:
    fw_data = firmware.read_bytes()
    mcu_crc = get_mcu_crc(len(fw_data), uart)
    print(f"FLASH CRC: {hex(mcu_crc)}")

    fw_crc = stm32_crc(fw_data)
    print(f"FW CRC: {hex(fw_crc)}")

    return mcu_crc == fw_crc


def flash(firmware: Path, uart: Path):
    check_call([STM32FLASH, "-R", "-w", str(firmware), "-v", str(uart)])


def run(uart: Path):
    check_call([STM32FLASH, "-R", "-g", hex(FLASH_START), str(uart)], stdout=DEVNULL)


def reset():
    print("HW reset")
    gpio.setup(RESET_GPIO, gpio.OUT)
    gpio.set(RESET_GPIO, 0)
    sleep(1 / 1000000)  # STM32G070 requires pulse longer than 350ns
    gpio.set(RESET_GPIO, 1)


if len(sys.argv) != 3:
    print("Error, pass firmware binary path and UART path", file=sys.stderr)
    exit(1)

firmware_path = Path(sys.argv[1])
if not firmware_path.exists():
    print("Firmware path does not exist.", file=sys.stderr)
    exit(2)

uart_path = Path(sys.argv[2])
if not uart_path.exists():
    print("UART path does not exist.", file=sys.stderr)
    exit(2)

try:
    reset()
    if check_crc_match(firmware_path, uart_path):
        print("Firmware CRC match, no update necessary")
    else:
        print("Firmware CRC mismatch, flashing MCU firmware.")
        flash(firmware_path, uart_path)
    print("Running MCU program")
    run(uart_path)
except Exception as exception:
    raise Exception("Failed to flash firmware") from exception
