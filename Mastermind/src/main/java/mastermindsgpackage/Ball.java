package mastermindsgpackage;

import org.academiadecodigo.simplegraphics.graphics.Color;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;


/**
 * Created by macha on 09/02/2016.
 */
public class Ball extends GameObject implements  Clickable{


    private BallColors color;
    // private int diameter;
    private Position position;
private Picture focus;

    private Picture picture;
    private Rectangle focusPointer;
    private boolean notSelectedYet;


    public  Ball(int posX, int posY){

        position = new Position(posX,posY);
        //this.diameter = diameter;
        setColor(BallColors.NONE);

        picture.draw();
        notSelectedYet = true;
    }

    public void activate(){
        if(focusPointer!=null){

            focusPointer.draw();
            return;
        }

        focusPointer = new Rectangle(position.getPosX(), position.getPosY()+(Config.BALL_DIAMETER/2)+2, 2,2);
        focusPointer.setColor(Color.WHITE);
        focusPointer.draw();
    }
    public void deActivate(){
        if(focusPointer!=null){
            focusPointer.delete();
        }

    }


    public void deletePicture(){
        picture.delete();
        notSelectedYet=true;

    }

    public boolean isNotSelectedYet() {
        return notSelectedYet;
    }

    public void setColor(BallColors color){

        //delete old picture
        if(picture != null) {
            picture.delete();
        }

        //set a new one
        this.color = color;

        picture = new Picture(position.getPosX(),position.getPosY(),color.getPath());
        picture.translate(-picture.getWidth()/2,-picture.getWidth()/2);

        if(color == BallColors.NONE){
            notSelectedYet=true;
        }else{
            notSelectedYet=false;
        }
    }



    public void drawPicture(){
        picture.draw();
        if(color == BallColors.NONE){
            notSelectedYet=true;
        }else{
            notSelectedYet=false;
        }


    }

    public void setNextColor(){

        int colorIndex = color.ordinal();

        if(colorIndex==BallColors.values().length-1){
            colorIndex = 1;
        }
        else{
            colorIndex += 1;
        }

       setColor(BallColors.values()[colorIndex]);
        //return BallColors.values()[colorIndex];
    }

    public void setPrevColor(){

        int colorIndex = color.ordinal();
        if(colorIndex<=1){
            colorIndex = BallColors.values().length-1;
        }
        else{
            colorIndex -= 1;
        }
        setColor(BallColors.values()[colorIndex]);
        //return BallColors.values()[colorIndex];
    }



    public BallColors getColor() {
        return color;
    }


    @Override
    public String ContextHelpText(){
        return "Chose a ball color to guess the sequence below";
    }

    @Override
    public boolean click(double mousePosX, double mousePosY, boolean isMenuItem){
        if(position.distanceBetweenPoints(mousePosX,mousePosY,this.position.getPosX(),this.position.getPosY()) <= Config.BALL_DIAMETER/ 2) {

            //setColor(getNextColor());
            setNextColor();
            drawPicture();
            activate();

            return true;
        }
        return false;
    }
}
