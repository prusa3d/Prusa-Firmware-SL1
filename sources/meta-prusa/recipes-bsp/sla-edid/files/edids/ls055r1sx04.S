/*
   EDID for Sharp LS055R1SX04 LCD panel bridged via Toshiba TC358870XBG.
   Refresh rate is kept at 36 Hz in order to allow for stable timing with
   the standard 148.5 MHz pixel clock utilized by standard resolutions
   and timings listed in CEA/EIA-861.
*/

/* Display */
#define HSYNC_POL 1
#define VSYNC_POL 1
#define YPULSE 1 /* VSYNC */
#define XPULSE 10 /* HSYNC */
#define YPIX 2560
#define XPIX 1440
#define VFREQ 36
#define DPI 537
#define CLOCK 148500
#define VERSION 1
#define REVISION 3
#define XOFFSET 100 /* HBP */
#define YOFFSET 3 /* VBP */
#define YBLANK 8 /* VBP + VSYNC + VFP */
#define XBLANK 166 /* HBP + HSYNC + HFP */
#define TIMING_NAME "LS055R1SX04"

/* Manufacturer */
#define MFG_LNX1 'T'
#define MFG_LNX2 'S'
#define MFG_LNX3 'B'
#define YEAR 2011
#define WEEK 28

#include "edid.h"

