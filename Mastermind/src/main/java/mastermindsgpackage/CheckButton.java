package mastermindsgpackage;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by macha on 14/02/2016.
 */
public class CheckButton extends GameObject implements Clickable{

    private Position position;

    private Picture picture;

    public  CheckButton(int posX, int posY){

        position = new Position(posX,posY);

        String filePath = Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "checkButton.png";

        picture = new Picture(Config.submitOffsetX, Config.submitOffsetY,filePath);
        //picture.translate(-picture.getWidth()/2,-picture.getWidth()/2);
        //drawPicture();

    }
/*

    public void activate(){
        if(focusPointer!=null){

            focusPointer.draw();
            return;
        }

        focusPointer = new Rectangle(position.getPosX(), position.getPosY()+(diameter/2)+2, 2,2);
        focusPointer.setColor(Color.WHITE);
        focusPointer.draw();
    }
    public void deActivate(){
        if(focusPointer!=null){
            focusPointer.delete();
        }

    }
*/


    public void deletePicture(){
        picture.delete();

    }



    public void drawPicture(){
        picture.draw();

    }

    public int getPosX() {
        return position.getPosX();
    }

    public int getPosY() {
        return position.getPosY();
    }

    public void setPosY(int newPosY) {

        picture.translate(0,newPosY-position.getPosY());
        position.setPosY(newPosY);
    }


    @Override
    public String ContextHelpText(){
        return "Check if sequence is correct";
    }

    @Override
    public boolean click(double mousePosX, double mousePosY, boolean isMenuItem){

        if(mousePosX > position.getPosX() &&  mousePosX < position.getPosX() + picture.getWidth() &&
                mousePosY > position.getPosY() &&  mousePosY < position.getPosY() + picture.getHeight() ) {

            deletePicture();
            return true;
        }
        return false;
    }
}
