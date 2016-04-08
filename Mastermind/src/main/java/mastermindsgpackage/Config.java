package mastermindsgpackage;

/**
 * Created by macha on 12/02/2016.
 */
public final class Config {

    //MENU
    protected static int menuOffsetX = 56;
    protected static int menuOffsetY = 38;
    protected static int menuSpacingX = 85;

    //File access
    protected static final String fileResourcesPath = "/";
    protected static final String ballAddToPath = "ball_";
   // protected static final String ballFocusAddToPath = "ball_focus_";
    protected static final String pinAddToPath = "pin_";
    protected static final String menuAddToPath = "menu_";
    protected static int currentThemeIndex = 0;
protected static String musicAddToPath = "Music/";
    protected static String musicExtensionAddToPath = ".mid";

    //access file in jar:



    //GRID DEFINITIONS
    protected static final int GRID_ROWS = 9;
    protected static final int GRID_COLUMNS = 4;

    //CORRECTIONS
    protected static  int X_Axis_Correction = 2;
    protected static  int Y_Axis_Correction = 26;
    protected static final int BORDER_X = 10;
    protected static final int BORDER_Y = 0;

    //BALLS
    protected static final int BALL_DIAMETER = 55;
    protected static int ballSpacingX = 76;
    protected static int ballSpacingY = 79;
    protected static int ballOffsetX = 141;
    protected static int ballOffsetY = 114;

    //PINS
    protected static final int PIN_DIAMETER = 10;
    protected static int pinSpacingXY = 28; //pins are displayed in a square x=y
    protected static int pinSpacingY = 79; //spacing between top left pins between rows of balls
    protected static int pinOffsetX = 455; //position of the top left corner in row 0
    protected static int pinOffsetY = 98; //position of the top left corner in row 0

    //SUBMIT BUTTON
    protected static int submitOffsetX = 436;//position of the top left corner in row 0
    protected static int submitOffsetY = 78;//position of the top left corner in row 0

    //CURRENT ROW HIGHLIGHT
    protected static int CurrentRowHighOffsetX = 86;//position of the top left corner in row 0
    protected static int CurrentRowHighOffsetY = 74;//position of the top left corner in row 0

    //GAME OVER
    protected static int GameOverOffsetX = 140;//position of the top left corner in row 0
    protected static int GameOverOffsetY = 866;//position of the top left corner in row 0




    public static void init(){
        if(System.getProperty("os.name").equals("Windows 10")){
            X_Axis_Correction = 8;
            Y_Axis_Correction = 31;

        }//Missing other systems configuration

    }




}
