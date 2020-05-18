/*
   EDID for Wisecoco TOP060MONO16202560 LCD panel bridged via Toshiba
   TC358870XBG.
   Delay after horizontal sync signal is significantly prolonged
   in order to allow for stable timing with the standard 148.5 MHz
   pixel clock and simultaneously keep horizontal scan frequency
   at ~131.6 kHz.
*/

/* Display */
#define CLOCK 148500 /* kHz */
#define XPIX 540
#define YPIX 2560
#define XBLANK 588
#define YBLANK 73
#define XOFFSET 522
#define XPULSE 10
#define YOFFSET 50
#define YPULSE 2
#define DPI 498
#define XSIZE 83
#define VFREQ 50 /* Hz */
#define TIMING_NAME "DXQ608"
/* No ESTABLISHED_TIMINGx_BITS */
#define HSYNC_POL 0
#define VSYNC_POL 0


/* Manufacturer */
#define MFG_LNX1 'L'
#define MFG_LNX2 'Z'
#define MFG_LNX3 'T'
#define SERIAL 0
#define YEAR 2017
#define WEEK 12

#include "edid.h"
