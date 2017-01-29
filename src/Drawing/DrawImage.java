package Drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

/**
 * Created by Carioca on 26/01/2017.
 */
public class DrawImage {
    static JFrame window;
    static DrawPanel graphicsWin;
    static final int CELL_SIZE=7;
    static final int V_CELLS_COUNT=90;
    static final int H_CELLS_COUNT=80;
    static boolean terminateSignal=false;
    static AutoIncrement drawingThread;

    public static void main(String[] args) {
        window = new JFrame();
        graphicsWin = new DrawPanel(CELL_SIZE, V_CELLS_COUNT, H_CELLS_COUNT);
        window.setSize(H_CELLS_COUNT*CELL_SIZE,V_CELLS_COUNT*CELL_SIZE + 40);
        window.setContentPane(graphicsWin);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(new LifeController());
        drawingThread = new AutoIncrement();
        window.setVisible(true);
        drawingThread.run();
//        while (!terminateSignal) {
//            drawingThread.run();
//            try {
//                drawingThread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    static class AutoIncrement extends Thread{
        boolean terminate = false;

        @Override
        public void run() {
            while(!terminate) {
                graphicsWin.Increment();
                window.setContentPane(graphicsWin);
                try {
                    this.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void TerminateThread(){
            this.terminate = true;
        }
    }

//    static class

    static class LifeController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
//        super.keyPressed(e);
            int key_code = e.getKeyCode();
            if(key_code == '\n'){
                drawingThread.TerminateThread();
                terminateSignal = true;
//                graphicsWin.increment();
//                window.setContentPane(graphicsWin);
            }
        }
    }

}
