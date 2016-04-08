package mastermindsgpackage;

import org.academiadecodigo.simplegraphics.graphics.Color;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;


/**
 * Created by macha on 09/02/2016.
 */
public class MenuIcon extends GameObject implements  Clickable{


    private Position position;
    private Picture picture;
    private int menuIndex;
private boolean on;

    public  MenuIcon(int posX, int posY, int menuIndex){

        this.position = new Position(posX,posY);
        this.menuIndex = menuIndex;
        setPicture();
        this.picture.draw();
        this.on = false;
    }


    public void deletePicture(){
        picture.delete();
    }


    public void setPicture(){

        //delete old picture
        if(picture != null) {
            picture.delete();
        }

        //set a new one

        if(on){
            picture = new Picture(position.getPosX(),position.getPosY(),filepath_on() );
        }else {
            picture = new Picture(position.getPosX(), position.getPosY(), filepath());
        }

        picture.translate(-picture.getWidth()/2,-picture.getWidth()/2);

    }

    public void changeState(){
        on = !on;
        setPicture();
        drawPicture();

    }

    public boolean getOnState(){
        return on;
    }


    private String filepath(){
    return Config.fileResourcesPath + Config.menuAddToPath + menuIndex + ".png";
    //example: "Resources/menu_0.png
}

    private String filepath_on(){
        return Config.fileResourcesPath + Config.menuAddToPath + menuIndex + "_on.png";
        //example: "Resources/menu_0.png
    }

    public void drawPicture(){
        picture.draw();
    }



    @Override
    public String ContextHelpText(){
        return "Chose a ball color to guess the sequence below";
    }

    @Override
    public boolean click(double mousePosX, double mousePosY, boolean notUsed){

        if(position.distanceBetweenPoints(mousePosX,mousePosY,this.position.getPosX(),this.position.getPosY()) <= Config.BALL_DIAMETER/ 2) {

            return true;
        }
        return false;
    }
}
