package mastermindsgpackage;


import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;

/**
 * Created by codecadet on 11/02/16.
 */
public class Game  {//implements KeyboardHandler, MouseHandler {


    //Keyboard and mouse
    private Mouse mice;
    private Keyboard keyboard;
    private KeyboardEvent[] keyboardEvent = new KeyboardEvent[91];
    protected boolean[] keyPressEventQueue = new boolean[91]; //initiate with false
    protected MouseEventQueue mouseQueue = new MouseEventQueue();


    boolean kidMode=false;

    private BallColors masterKey[] = {null, null, null, null};

    GameObject[][] balls = new Ball[Config.GRID_ROWS][Config.GRID_COLUMNS];
    GameObject[][] pins = new Pin[Config.GRID_ROWS][Config.GRID_COLUMNS];
    GameObject[] solution = new SolutionBall[Config.GRID_COLUMNS];
    GameObject[] menuIcons = new MenuIcon[BallColors.values().length-1];//create 1 icon for each ball color except 1 (the transparent one)

    private static final boolean MODE_DEBUG = false; //change this to show the master key

    private boolean helpMenu_1_IsVisible = false;
    private boolean helpMenu_2_IsVisible = false;

    private boolean soundIsOnFlag = true;
    MidiSound sound = new MidiSound();


    protected Field field=new Field();

    private int focusRow;
    private int focusCol;


    PlayerInputEvents pie;



    public Game() {

        focusRow = 0;
        focusCol = -1;

        initBallsAndPins(focusRow);
        generateNewMasterKey();

        initMenu();

        pie = new PlayerInputEvents(this);


        changeTheme();
    }


    public void start() throws  InterruptedException{



        while (true) {

            Thread.sleep(250);

            //updateKeyEvents();//myPlayer.updateKeyEvents(this);
            //updateMouseEvents();//myPlayer.updateMouseEvents(this);


            field.updateCurrentRowHighlight(focusRow);

            //if balls are chosen show the submit button
            if (isSequenceChosen()) {
                field.updateCheckButton(focusRow);
            }

        }
    }


    private void initMenu(){
        for (int i = 0; i < menuIcons.length; i++) {

            menuIcons[i] = new MenuIcon(Config.menuOffsetX + Config.menuSpacingX*i, Config.menuOffsetY,i);

        }
    }


    private void initBallsAndPins(int row) {

        //for (int i = 0; i < Config.GRID_ROWS; i++) {
        for (int j = 0; j < Config.GRID_COLUMNS; j++) {

            balls[row][j] = new Ball(field.gridBallColumnToX(j), field.gridBallRowToY(row));
            pins[row][j] = new Pin(field.gridPinColumnToX(j), field.gridPinRowToY(row, j));

        }

    }

    public boolean isSequenceChosen() {
        if(focusRow<0 || focusRow>8) return false; //end of game

        for (int j = 0; j < Config.GRID_COLUMNS; j++) {

            if (((Ball) (balls[focusRow][j])).isNotSelectedYet()) {
                return false;
            }

        }
        return true;
    }

    private void resetGame() {

        focusRow = 0;
        focusCol = -1;

        field.deleteCheckButton();
        field.deleteGameOver();

        for (int i = 0; i < Config.GRID_ROWS; i++) {
            for (int j = 0; j < Config.GRID_COLUMNS; j++) {

                if (balls[i][j] != null) {
                    ((Ball) balls[i][j]).deletePicture();
                }

                if( pins[i][j]!=null ){
                    ((Pin) pins[i][j]).deletePicture();
                }

            }

        }

        if(solution != null && solution[0] != null ){
            for (int i = 0; i < solution.length; i++) {
                ((SolutionBall)(solution[i])).deletePicture();
            }
        }



        generateNewMasterKey();
        initBallsAndPins(focusRow);
        //((MenuIcon) (menuIcons[4])).changeState();
    }

    public void submitGuess() {

        BallColors[] guess = new BallColors[Config.GRID_COLUMNS]; //4 balls

        for (int i = 0; i < balls[focusRow].length; i++) {
            guess[i] = ((Ball) (balls[focusRow][i])).getColor();
        }

        int corrects = 0;
        int switched = 0;
        int guessIgnore[] = {0, 0, 0, 0};
        int masterKeyIgnore[] = {0, 0, 0, 0};
        boolean allCorrect = false;


        //Kids mode -------------------------------------------
        if(kidMode){
            field.deleteCheckButton();

            for (int i = 0; i < 4; i++) {

                if (masterKey[i] == guess[i] ) {
                    masterKeyIgnore[i] = 1;
                    guessIgnore[i] = 1;
                    corrects++;
                    ((Pin) pins[focusRow][i]).setType(PinTypes.RIGHT);
                    ((Pin) pins[focusRow][i]).drawPicture();

                }

            }


            //Count number of switched
            for (int i = 0; i < 4; i++) {
                if (masterKeyIgnore[i] == 1){
                    continue; //already considered
                }
                for (int k = 0; k < 4; k++) {
                    if (guessIgnore[k] == 1){
                        continue; //already considered
                    }
                    if (masterKey[i] == guess[k] && i != k) {
                        masterKeyIgnore[i] = 1;
                        guessIgnore[k] = 1;
                        ((Pin) pins[focusRow][k]).setType(PinTypes.SWITCHED);
                        ((Pin) pins[focusRow][k]).drawPicture();
                        break;
                    }
                }
            }


            for (int i = 0; i < 4; i++) {
                if (guessIgnore[i] == 0){
                    ((Pin) pins[focusRow][i]).setType(PinTypes.WRONG);
                    ((Pin) pins[focusRow][i]).drawPicture();
                }
            }


            if(corrects==4 || focusRow>7){
                gameOver(corrects==4);
                return;
            }

            if (focusRow < 8) {
                nextRound();
            }



            return;

        }//end kid mode -------------------------------------------














        //Count number of corrects
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                if (guessIgnore[k] == 1){
                    continue; //already considered
                }
                if (masterKey[i] == guess[k] && i == k) {
                    masterKeyIgnore[i] = 1;
                    guessIgnore[k] = 1;
                    corrects++;
                    break;
                }
            }
        }
        //Count number of switched
        for (int i = 0; i < 4; i++) {
            if (masterKeyIgnore[i] == 1){
                continue; //already considered
            }
            for (int k = 0; k < 4; k++) {
                if (guessIgnore[k] == 1){
                    continue; //already considered
                }
                if (masterKey[i] == guess[k] && i != k) {
                    masterKeyIgnore[i] = 1;
                    guessIgnore[k] = 1;
                    switched++;
                    break;
                }
            }
        }

        if(corrects==4) allCorrect=true;

        field.deleteCheckButton();
        PinTypes pinType = PinTypes.RIGHT;
        for (int i = 0; i < pins[focusRow].length; i++) {

            if (corrects == 0) {
                if (switched == 0) {
                    break;
                }
                pinType = PinTypes.SWITCHED;
                switched--;
            } else {
                corrects--;
            }

            ((Pin) pins[focusRow][i]).setType(pinType);
            ((Pin) pins[focusRow][i]).drawPicture();

        }

        if(allCorrect || focusRow>7){
            gameOver(allCorrect);
            return;
        }

        if (focusRow < 8) {
            nextRound();
        }

    }

    private void nextRound() {
        focusRow += 1;
        focusCol = -1;

        initBallsAndPins(focusRow);
    }
    private void gameOver(boolean win){

        field.drawGameOver();

        if(win){

        }
        focusRow=9;
        focusCol=-1;
        showMasterKey();


    }




    private void generateNewMasterKey() {

        for (int j = 0; j < masterKey.length; j++) {
            masterKey[j] = BallColors.values()[random(1, BallColors.values().length - 1)];
        }

        if(MODE_DEBUG) {showMasterKey();}

    }

    private void showMasterKey() {


        for (int index_X = 0; index_X < Config.GRID_COLUMNS; index_X++) {

            solution[index_X] = new SolutionBall(field.gridBallColumnToX(index_X), field.gridBallRowToY(Config.GRID_ROWS));
            ((SolutionBall) solution[index_X]).setColor(masterKey[index_X]);
            ((SolutionBall) solution[index_X]).drawPicture();
        }
    }



    private void changeTheme(){

        field.drawBackground();
        field.createCheckButton();
        field.createCurrentRowHighlight(kidMode);
        if(focusRow==9) {
            field.drawGameOver();
        }

        //Balls pins and solution balls
        for (int i = 0; i <= focusRow; i++) {
            for (int j = 0; j < Config.GRID_COLUMNS; j++) {
                if(i==9){
                    ((SolutionBall)(solution[j])).setColor(((SolutionBall)(solution[j])).getColor());
                    ((SolutionBall)(solution[j])).drawPicture();
                }else{

                    ((Ball)(balls[i][j])).setColor(((Ball)(balls[i][j])).getColor());
                    ((Ball)(balls[i][j])).drawPicture();
                    ((Pin)(pins[i][j])).setType(((Pin) (pins[i][j])).getPinType());
                    ((Pin)(pins[i][j])).drawPicture();
                }

            }

        }


        //Menu Items
        for (int i = 0; i < menuIcons.length; i++) {
            //menuIcons[i] = new MenuIcon(Config.menuOffsetX + Config.menuSpacingX*i, Config.menuOffsetY,i);
            ((MenuIcon)(menuIcons[i])).setPicture();
            ((MenuIcon)(menuIcons[i])).drawPicture();

        }

        if(soundIsOnFlag){
            sound.stop();
            sound.play(Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "music.mid");

            //sound.play("/Classic/music.mid");
        }

        //((MenuIcon) (menuIcons[1])).changeState();
    }







    private int random(int min, int max) {
        return ((int) (Math.random() * (max - min + 1) + min));
    }



    public void updateKeyEvents(){



        //Key SPACE - Same as pressing the check button with mouse
        if(keyPressEventQueue[KeyboardEvent.KEY_SPACE] ){
            keyPressEventQueue[KeyboardEvent.KEY_SPACE] = false;

            if(focusRow<0 || focusRow>8 ) return;

            if (isSequenceChosen()){
                if(focusCol!=-1){
                    ((Ball)(balls[focusRow][focusCol])).deActivate();

                }
                submitGuess();
            }
        }



        if(keyPressEventQueue[KeyboardEvent.KEY_RIGHT]){

            keyPressEventQueue[KeyboardEvent.KEY_RIGHT]=false;

            if(focusRow<0 || focusRow>8 ) return;

            //clear focus on current ball
            if(focusCol >= 0){
                ((Ball)(balls[focusRow][focusCol])).deActivate();
            }

            //go to leftmost ball if current focus is on the rightmost
            if(focusCol==Config.GRID_COLUMNS-1){
                focusCol=0;
            } else {
                focusCol+=1;
            }

            ((Ball)(balls[focusRow][focusCol])).activate();
        }

        if(keyPressEventQueue[KeyboardEvent.KEY_LEFT]) {
            keyPressEventQueue[KeyboardEvent.KEY_LEFT]=false;

            if(focusRow<0 || focusRow>8 ) return;

            //clear focus on current ball
            if(focusCol >= 0){
                ((Ball)(balls[focusRow][focusCol])).deActivate();
            }

            //go to leftmost ball if current focus is on the rightmost
            if(focusCol==0 || focusCol == -1) {
                focusCol = Config.GRID_COLUMNS-1;
            } else {
                focusCol -= 1;
            }
            ((Ball)(balls[focusRow][focusCol])).activate();



        }
        if(keyPressEventQueue[KeyboardEvent.KEY_UP]) {
            keyPressEventQueue[KeyboardEvent.KEY_UP] = false;

            if(focusCol<0 || focusRow>8 ) return;
            //((Ball)(balls[focusRow][focusCol])).setColor( ((Ball)balls[focusRow][focusCol]).getNextColor());
            ((Ball)(balls[focusRow][focusCol])).setNextColor();
            ((Ball)(balls[focusRow][focusCol])).drawPicture();
        }
        if(keyPressEventQueue[KeyboardEvent.KEY_DOWN]) {
            keyPressEventQueue[KeyboardEvent.KEY_DOWN] =false;
            if(focusCol<0 || focusRow>8 ) return;
            //((Ball)(balls[focusRow][focusCol])).setColor(((Ball) balls[focusRow][focusCol]).getPrevColor());
            ((Ball)(balls[focusRow][focusCol])).setPrevColor();
            ((Ball)(balls[focusRow][focusCol])).drawPicture();
        }



    }

    public void updateMouseEvents(){

        while(!mouseQueue.isEmpty()) {

            MouseEvent e = mouseQueue.poll();

            double mouseX = e.getX()-Config.X_Axis_Correction;
            double mouseY = e.getY()-Config.Y_Axis_Correction;


            //if help menu 2 is open turn it off
            if(helpMenu_2_IsVisible){
                field.deleteHelpWindow_2();
                mouseX = 0; //force mouse to click 0,0
                mouseY = 0;
                helpMenu_2_IsVisible = false;
                //((MenuIcon) (menuIcons[0])).changeState();
            }

            //if help menu 1 is open turn it off
            if(helpMenu_1_IsVisible){
                field.drawHelpWindow_2();
                field.deleteHelpWindow_1();
                mouseX = 0; //force mouse to click 0,0
                mouseY = 0;
                helpMenu_1_IsVisible = false;

                helpMenu_2_IsVisible = true;
            }




            boolean noBallWasClicked = true; //used to remove focus if click is not on any ball

            for(int j=0;j<Config.GRID_COLUMNS;j++) {

                if(focusRow<0 || focusRow>8) break;

                //check if click is inside the ball
                if (((Ball) (balls[focusRow][j])).click(mouseX, mouseY,false)) {//distance <= BALL_GUESSES_DIAMETER/2) {
                    focusCol = j;
                    noBallWasClicked = false;
                } else{
                    ((Ball) (balls[focusRow][j])).deActivate();
                }

            }

            if(noBallWasClicked) {
                focusCol =-1;
            }



            //check if click is in the submit
            if(mouseX > Config.submitOffsetX &&
                    mouseX < Config.submitOffsetX + field.getCheckButtonWidth() &&
                    mouseY > Config.submitOffsetY + (Config.ballSpacingY*focusRow) &&
                    mouseY < Config.submitOffsetY + (Config.ballSpacingY*focusRow) + field.getCheckButtonHeight()     ){

                if (isSequenceChosen()){
                    submitGuess();
                }
            }





            //check menu items
            if (((MenuIcon) (menuIcons[0])).click(mouseX, mouseY,true)) { //menu 0 - help screen
                //((MenuIcon) (menuIcons[0])).changeState();
                field.drawHelpWindow_1();
                helpMenu_1_IsVisible = true;
            }
            if (((MenuIcon) (menuIcons[1])).click(mouseX, mouseY,true)) { //menu 1 - change theme
                //((MenuIcon) (menuIcons[1])).changeState();

                //change theme
                int themeAdd = 1;

                if(Config.currentThemeIndex == Themes.values().length-2 && !Themes.CustomDirectoryExists()){//next theme is the custom but does not exist
                    themeAdd = 2;
                }

                Config.currentThemeIndex = (Config.currentThemeIndex + themeAdd) % Themes.values().length;
                changeTheme();

            }
            if (((MenuIcon) (menuIcons[2])).click(mouseX, mouseY,true)) { //menu 2 - kid mode
                kidMode = !kidMode;
                field.createCurrentRowHighlight(kidMode);


            }
            if (((MenuIcon) (menuIcons[3])).click(mouseX, mouseY,true)) { //menu 3 - sound on/off

                soundIsOnFlag = !soundIsOnFlag;
                /*if(soundIsOnFlag){
                    music.stop();

                }else {
                    music.playNext(Config.fileResourcesPath + Config.musicAddToPath + "Movie_Themes_-_Addicted_To_Love.mid");

                }
                soundIsOnFlag = !soundIsOnFlag; //((MenuIcon) (menuIcons[3])).changeState();*/

                if(soundIsOnFlag){
                    sound.stop();
                    sound.play(Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + "music.mid");
                    //sound.play("/Classic/music.mid");
                }else{
                    sound.stop();
                }

            }
            if (((MenuIcon) (menuIcons[4])).click(mouseX, mouseY,true)) { //menu 4 - reset game
                //((MenuIcon) (menuIcons[4])).changeState();
                resetGame();
            }
            if (((MenuIcon) (menuIcons[5])).click(mouseX, mouseY,true)) { //menu 5 - power off
                //((MenuIcon) (menuIcons[5])).changeState();
                System.exit(0);
            }

        }

    }

public void updateMouseMove(double mouseX, double mouseY){

    if(helpMenu_1_IsVisible || helpMenu_2_IsVisible){
        return;
    }



    for(int i=0; i<6; i++) {
        if(i==2 || i==3){
            continue;
        }
        //check menu items
        if (((MenuIcon) (menuIcons[i])).click(mouseX, mouseY, true)) { //menu 0 - help screen
            if (!((MenuIcon) (menuIcons[i])).getOnState()) {
                ((MenuIcon) (menuIcons[i])).changeState();
            }
        } else {
            if (((MenuIcon) (menuIcons[i])).getOnState()) {
                ((MenuIcon) (menuIcons[i])).changeState();
            }

        }
    }



    //process menu 2  separately
    if (((MenuIcon) (menuIcons[2])).click(mouseX, mouseY, true)) { //menu 0 - help screen
        if(!((MenuIcon) menuIcons[2]).getOnState()){
            ((MenuIcon) (menuIcons[2])).changeState();
        }
    } else {
        if(kidMode && !((MenuIcon) menuIcons[2]).getOnState()){
            ((MenuIcon) (menuIcons[2])).changeState();
        }else if(!kidMode && ((MenuIcon) menuIcons[2]).getOnState()){
            ((MenuIcon) (menuIcons[2])).changeState();
        }
    }
//process menu 3  separately
    if (((MenuIcon) (menuIcons[3])).click(mouseX, mouseY, true)) { //menu 0 - help screen
        if(!((MenuIcon) menuIcons[3]).getOnState()){
            ((MenuIcon) (menuIcons[3])).changeState();
        }
    } else {
        if(soundIsOnFlag && !((MenuIcon)menuIcons[3]).getOnState()){
            ((MenuIcon) (menuIcons[3])).changeState();
        }else if(!soundIsOnFlag && ((MenuIcon)menuIcons[3]).getOnState()){
            ((MenuIcon) (menuIcons[3])).changeState();

        }
    }


}



}