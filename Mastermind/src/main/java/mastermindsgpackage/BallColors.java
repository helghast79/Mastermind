package mastermindsgpackage;

/**
 * Created by codecadet on 09/02/16.
 */

import org.academiadecodigo.simplegraphics.graphics.Color;

/**
 * Created by macha on 08/02/2016.
 */
public enum BallColors {

    NONE(null,"NONE.png"),
    RED(Color.RED, "RED.png"),
    BLUE(Color.BLUE, "BLUE.png"),
    WHITE(Color.WHITE, "WHITE.png"),
    PINK(Color.PINK, "PINK.png"),
    GREEN(Color.GREEN, "GREEN.png"),
    YELLOW(Color.YELLOW, "YELLOW.png");


    private Color color;
    private String path;

    BallColors(Color color, String path){
        this.color = color;
        this.path = path;
    }

    public Color getColor() {
        return color;
    }

    public BallColors getNextColor(){

        int index = this.ordinal();//index of current color

        if(index==BallColors.values().length-1){
            index =1;
        }else{
            index+=1;
        }
        return BallColors.values()[index];

    }

    public BallColors getPrevColor(){

        int index = this.ordinal();//index of current color

        if(index<=1){
            index =BallColors.values().length-1;
        }else{
            index-=1;
        }
        return BallColors.values()[index];

    }

    public String getPath() {
         return (Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + Config.ballAddToPath + path);
        //example: "Master Mind Simple Graphics/Resources/cla_ball_BLUE.png
    }


}
