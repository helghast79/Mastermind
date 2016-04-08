package mastermindsgpackage;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by macha on 13/02/2016.
 */
public class SolutionBall extends GameObject{


    private BallColors color;
    private Position position;

    private Picture picture;



    public  SolutionBall(int posX, int posY ){

        position = new Position(posX,posY);

        setColor(BallColors.NONE);

        picture.draw();


    }



    public void deletePicture(){
        picture.delete();

    }


    public void setColor(BallColors color) {


        //delete old picture
        if (picture != null) {
            picture.delete();
        }

        //set a new one
        this.color = color;
        picture = new Picture(position.getPosX(), position.getPosY(), color.getPath());
        picture.translate(-picture.getWidth() / 2, -picture.getWidth() / 2);
    }
    public void drawPicture(){

        picture.draw();

    }


    public BallColors getColor() {
        return color;
    }


    @Override
    public String ContextHelpText(){
        return "This is the correct sequence";
    }


}


