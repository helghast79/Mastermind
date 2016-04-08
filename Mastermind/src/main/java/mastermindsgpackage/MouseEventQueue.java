package mastermindsgpackage;

import javafx.event.Event;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;


import java.util.LinkedList;

import java.util.Queue;

/**
 * Created by codecadet on 15/02/16.
 */
public class MouseEventQueue {



    private Queue<MouseEvent> queue;


    public MouseEventQueue(){
        queue = new LinkedList<MouseEvent>();
    }


    public boolean offer(MouseEvent e){
        return queue.offer(e);
    }


    public boolean isEmpty(){

        return queue.isEmpty();

    }

    public MouseEvent poll(){
        return queue.poll();

    }


}


