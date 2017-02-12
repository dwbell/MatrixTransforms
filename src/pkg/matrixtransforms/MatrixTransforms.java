package pkg.matrixtransforms;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import pkg.matrixtransforms.input.*;
import pkg.matrixtransforms.vector.*;

public class MatrixTransforms extends JFrame implements Runnable {

    private static final Dimension DIM = new Dimension(1280, 720);
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private MouseInput mouse;
    private KeyboardInput keyboard;

    //Declare Objects
    private static VectorObject autoObj;
    private static VectorObject keyObj;
    private static VectorObject mouseObj;

    public MatrixTransforms() {
    }

    /*
    Name: createAndShowGUI
    Param: N/A
    Desc: Creates the GUI by setting up the canvas, initializing
    the mouse and keyboard listeners, setting the buffer strategy,
    and finally starting the game loop. 
     */
    protected void createAndShowGUI() {
        
        Canvas canvas = new Canvas();
        canvas.setSize(DIM);
        canvas.setBackground(Color.BLACK);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Matrix Transformation");
        setIgnoreRepaint(true);
        pack();
        // Add key listeners
        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);
        // Add mouse listeners
        mouse = new MouseInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);
        disableCursor();
        setVisible(true);
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        canvas.requestFocus();
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    Name disableCursor
    Param: N/A
    Desc: Disables cursor so mouseCursor class to 
    add a new one
     */
    public void disableCursor() {
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage("");
        Point point = new Point(0, 0);
        String name = "Crosshair";
        Cursor cursor = tk.createCustomCursor(image, point, name);
        setCursor(cursor);
    }

    /*
    Name: run
    Param: n/a
    Desc: Runnables required method call. Here is where
    the game loop starts. 
     */
    public void run() {
        
        running = true;
        initialize();
        while (running) {
            gameLoop();
        }
    }

    /*
    Name: gameLoop
    Param: N/A
    Desc: Calls upon three control methods to handle the 
    the games workload in 
     */
    private void gameLoop() {
        
        processInput();
        updateWorld();
        renderFrame();
        sleep(10L);
    }

    /*
    Name: renderFrame
    Param: N/A
    Desc: Renders each frame of the game
     */
    private void renderFrame() {
        
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    /*
    Name: sleep
    Param: long sleep
    Desc: Utility method to allow for a small break from the 
    above run() method to free some resources. 
     */
    private void sleep(long sleep) {
        
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

    /*
    Name: initialize
    Param: N/A
    Desc: Sets up the individual objects with appropiate constructor
    parameters. 
     */
    private void initialize() {
        
        autoObj = new AutomaticObject(DIM, null, null);
        keyObj = new KeyboardObject(DIM, null, keyboard);
        mouseObj = new MouseObject(DIM, mouse, null);
    }

    /*
    Name: processInput
    Param: N/A
    Desc: Polls both the mouse and keyboard for updated values. 
    It will then call upon the objects that need to have access for
    basic user inputted commands.
     */
    private void processInput() {
        
        keyboard.poll();
        mouse.poll();
        
        keyObj.processInput();      //Keyboard
        mouseObj.processInput();    //Mouse
    }

    /*
    Name: updateWorld
    Param: N/A
    Desc: Updates the world matrix by performing 
    transformations for each respected object.
     */
    private void updateWorld() {
        
        autoObj.updateWorld();
        keyObj.updateWorld();
        mouseObj.updateWorld();
    }

    /*
    Name: render
    Param: Graphics g
    Desc: Does the actual rendering of the objects after
    input and transformations have been applied.
     */
    private void render(Graphics g) {
        
        autoObj.render(g);
        keyObj.render(g);
        mouseObj.render(g);
    }

    /*
    Name: onWindowClosing
    Param: N/A
    Desc: On window close, kill the game loop, 
    wait for thread to die. 
     */
    protected void onWindowClosing() {
        
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * **** MAIN *****
     */
    public static void main(String[] args) {
        
        final MatrixTransforms app = new MatrixTransforms();
        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                app.onWindowClosing();
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.createAndShowGUI();
            }
        });
    }

}
