package mastermindsgpackage;


import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by macha on 12/02/2016.
 */
public class Field {

    private Picture CurrentRowHighlight;
    private Picture checkButton;
    private Picture background;
    private Picture gameOver;
    private Picture help1;
    private Picture help2;

    public Field(){


    }
    public void createCurrentRowHighlight(boolean kidMode){
        if(CurrentRowHighlight != null){
            CurrentRowHighlight.delete();
        }

        String filePath = Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "currentRowHighlight_" + (kidMode ? "kid" : "men") + ".png";
        //example: "Master Mind Simple Graphics/Resources/cla_currentRowHighlight.png

        CurrentRowHighlight = new Picture(Config.CurrentRowHighOffsetX,Config.CurrentRowHighOffsetY,filePath);

    }
    public void updateCurrentRowHighlight(int currentRow){

        CurrentRowHighlight.translate(0, Config.CurrentRowHighOffsetY + (Config.ballSpacingY * currentRow) - CurrentRowHighlight.getY());
        CurrentRowHighlight.draw();
    }

    public void createCheckButton(){
        if(checkButton != null){
            checkButton.delete();
        }

        String filePath = Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "checkButton.png";
        //example: "Master Mind Simple Graphics/Resources/cla_checkButton.png

        checkButton = new Picture(Config.submitOffsetX, Config.submitOffsetY,filePath);

    }
    public void updateCheckButton(int currentRow){
        if(checkButton == null) {
            createCheckButton();
        }
        checkButton.translate( 0, Config.submitOffsetY + (Config.ballSpacingY*currentRow) - checkButton.getY());
        checkButton.draw();
    }
    public void deleteCheckButton(){
        checkButton.delete();
    }
    public int getCheckButtonWidth(){
        return checkButton.getWidth();
    }
    public int getCheckButtonHeight(){
        return checkButton.getHeight();
    }

    public void drawHelpWindow_1(){
        if(help1 != null) {
            help1.delete();
        }

        String filePath = Config.fileResourcesPath + "help1.jpg";
        //example: "Resources/help1.jpg

        help1 = new Picture(Config.BORDER_X,Config.BORDER_Y,filePath);
        help1.draw();
    }
    public void drawHelpWindow_2(){
        if(help2 != null) {
            help2.delete();
        }

        String filePath = Config.fileResourcesPath + "help2.jpg";
        //example: "Resources/help2.jpg

        help2 = new Picture(Config.BORDER_X,Config.BORDER_Y,filePath);
        help2.draw();
    }


    public void deleteHelpWindow_1(){
        help1.delete();
    }
    public void deleteHelpWindow_2(){
        help2.delete();
    }

    public void drawBackground(){
        if(background != null) {
            background.delete();
        }

        String filePath = Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "background.jpg";
        //example: "Resources/cla_background.jpg

        background = new Picture(Config.BORDER_X,Config.BORDER_Y,filePath);
        background.draw();
    }


    public void drawGameOver(){
        if(gameOver != null) {
            gameOver.delete();
        }

        String filePath = Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "Game_Over.png";
        //example: "Master Mind Simple Graphics/Resources/cla_Game_Over.png

        gameOver = new Picture(Config.GameOverOffsetX,Config.GameOverOffsetY,filePath);
        gameOver.draw();
    }
    public void deleteGameOver(){
        if(gameOver != null) {
            gameOver.delete();
        }
    }



    private void drawGrid(){


        Rectangle[][] rectangles = new Rectangle[Config.GRID_ROWS][Config.GRID_COLUMNS];
        Rectangle[][] squares = new Rectangle[Config.GRID_ROWS][Config.GRID_COLUMNS];

        for (int i = 0; i < Config.GRID_ROWS; i++) {
            for(int j = 0; j < Config.GRID_COLUMNS; j++){

                Position posBall = new Position(gridBallColumnToX(j),gridBallRowToY(i));

                rectangles[i][j] = new Rectangle(posBall.getPosX(), posBall.getPosY(),3,3);
                rectangles[i][j].setColor(Color.RED);
                rectangles[i][j].draw();


                Position posPin = new Position(gridPinColumnToX(j),gridPinRowToY(i,j));

                squares[i][j] = new Rectangle(posPin.getPosX(), posPin.getPosY(),2,2);
                squares[i][j].setColor(Color.WHITE);
                squares[i][j].draw();

            }
        }



    }

    public int gridBallColumnToX(int columnIndex){
        return Config.ballOffsetX + (columnIndex * Config.ballSpacingX) ;
    }

    public int gridBallRowToY(int rowIndex){
        return Config.ballOffsetY + (rowIndex * Config.ballSpacingY) ;
    }

    public int gridPinColumnToX(int columnIndex){
        columnIndex = columnIndex % 2; //pin has only 2 columns but maintain the structure of 4 as ball structure
        return Config.pinOffsetX + (columnIndex * Config.pinSpacingXY) ;
    }

    public int gridPinRowToY(int rowIndex, int columnIndex){

        return Config.pinOffsetY + (rowIndex * Config.pinSpacingY) + (columnIndex>1 ? Config.pinSpacingXY : 0) ;
    }

}
