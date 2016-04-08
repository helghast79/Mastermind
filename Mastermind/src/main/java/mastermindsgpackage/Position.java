package mastermindsgpackage;

/**
 * Created by macha on 12/02/2016.
 */
public class Position {


    private int posX;
    private int posY;

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;

    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public double distanceBetweenPoints(double p1_X, double p1_Y, double p2_X, double p2_Y){
        return Math.sqrt(Math.pow((p1_X - p2_X),2)+ Math.pow((p1_Y - p2_Y),2));
    }
}
