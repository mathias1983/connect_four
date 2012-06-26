package config;

public class GuiConfig
{
    // screen
    public static final int SCREEN_WIDTH  = 800;
    public static final int SCREEN_HEIGHT = 600;

    //board
    public static final int   BOARD_COLUMNS = 7; //7;
    public static final int   BOARD_ROWS    = 6; //6;
    
    static int w = SCREEN_WIDTH / BOARD_COLUMNS;
    static int h = SCREEN_HEIGHT / BOARD_ROWS;
    static int l = (h>w)?w:h;
    
    public static final float BOARD_WIDTH   = (BOARD_COLUMNS * l); // 400;
    public static final float BOARD_HEIGHT  = (BOARD_ROWS * l); //343;

    //symbols
    public static final int MARGIN   = 10;
    public static final int STROKE   = 10;

    //Animation
    public static final int SPEED                = 100;  // 10 bis 100
    public static final int TRANSPARENCY_SPEED   = 30;  // 5 bis 30
}

