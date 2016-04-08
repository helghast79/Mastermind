package mastermindsgpackage;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

/**
 * Created by codecadet on 22/02/16.
 */
public class PlayerInputEvents implements KeyboardHandler, MouseHandler {



    private Mouse mice;
    private Keyboard keyboard;
    private KeyboardEvent[] keyboardEvent = new KeyboardEvent[91];


    Game myGame;


    public PlayerInputEvents(Game myGame){
        this.myGame = myGame;

        setMouse();
        setKeyboard();

    }





    //Keyboard and mouse functions
    private void setMouse(){
        //Initiate mouse event listener
        mice = new Mouse(this);
        mice.addEventListener(MouseEventType.MOUSE_CLICKED);
        mice.addEventListener(MouseEventType.MOUSE_MOVED);

    }
    private void setKeyboard(){

        //Initiate keyboard event listener
        keyboard = new Keyboard(this);

        for (int i = 0; i <91; i++) {
            keyboardEvent[i] = new KeyboardEvent();
            keyboardEvent[i].setKey(i);
            keyboardEvent[i].setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEvent[i]);
        }
    }





    @Override
    public void keyPressed(KeyboardEvent e) {


        myGame.keyPressEventQueue[e.getKey()] = true;
        myGame.updateKeyEvents();
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        myGame.updateMouseMove(e.getX()-Config.X_Axis_Correction, e.getY()-Config.Y_Axis_Correction);
    }

    @Override
    public void mouseClicked(MouseEvent mousePos) {
        myGame.mouseQueue.offer(mousePos);
        myGame.updateMouseEvents();
    }
}
