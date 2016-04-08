package mastermindsgpackage;


import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by macha on 09/02/2016.
 */
public class Pin extends GameObject{

    private PinTypes pinType;
    private Position position;
    private Picture picture;



    public  Pin(int posX, int posY){

        this.position = new Position(posX,posY);
        setType(PinTypes.NONE);
        picture.draw();
    }


    public void deletePicture(){

            picture.delete();

    }

    public void setType(PinTypes pinType){
        //delete old picture
        if(picture != null) {
            picture.delete();
        }

        this.pinType = pinType;

        picture = new Picture(position.getPosX(), position.getPosY(), pinType.getPath());

        picture.translate(-picture.getWidth()/2,-picture.getWidth()/2);

    }

    public void drawPicture(){
        picture.draw();
    }

    public PinTypes getPinType() {
        return pinType;
    }

    public Position getPosition() {
        return position;
    }

    public Picture getPicture() {
        return picture;
    }
}
