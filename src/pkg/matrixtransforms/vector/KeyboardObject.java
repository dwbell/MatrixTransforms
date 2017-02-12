package pkg.matrixtransforms.vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import pkg.matrixtransforms.input.KeyboardInput;
import pkg.matrixtransforms.input.MouseInput;

public class KeyboardObject extends VectorObject {

    private final float vx = 1.4f;    //Rate of change x
    private final float vy = 1.4f;    //Rate of change y

    public KeyboardObject(Dimension dim, MouseInput mouse, KeyboardInput keyboard) {
        super(dim, mouse, keyboard);

        initialize();

    }

    /*
    Name: initialize
    Param: N/A
    Desc: Sets up the hexagon vector object, as well as
    the world matrix, and initializes its start point to 
    center of screen.
     */
    private void initialize() {

        this.rot = 0.0f;
        this.rotStep = (float) Math.toRadians(1);

        //Hexagon
        this.polygon = new Vector2f[]{new Vector2f(5, -9), new Vector2f(-5, -9),
            new Vector2f(-10, 0), new Vector2f(-5, 9), new Vector2f(5, 9), new Vector2f(10, 0)};

        //World matrix for each point
        this.world = new Matrix3x3f();

        //Set to center of screen
        this.tx = dim.width / 2;
        this.ty = dim.height / 2;
    }

    /*
    Name: ProcessInput
    Param: N/A
    Desc: Processes keyboard input from user with 
    respect to rotation of the object via Q, E and Space
    as well as translation of the object via WASD keys. 
     */
    @Override
    public void processInput() {

        //Rotations
        rot += rotStep;

        //Rotate counter clockwise
        if (keyboard.keyDown(KeyEvent.VK_Q)) {
            //Ramp up to maximum rotate speed
            if (rotStep >= -.5) {
                rotStep += (float) Math.toRadians(-.05f);
            }
        }

        //Rotate clockwise
        if (keyboard.keyDown(KeyEvent.VK_E)) {
            //Ramp up to maximum rotate speed
            if (rotStep <= .5) {
                rotStep += (float) Math.toRadians(.05f);
            }
        }

        //Flip rotational direction
        if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
            rotStep = (rotStep * - 1);
        }

        /*Collision detection and translation*/
        if ((ty - 11) > 0) {
            if (keyboard.keyDown(KeyEvent.VK_W)) {
                ty -= vy;
            }
        }

        if ((ty + 11) < dim.height) {
            if (keyboard.keyDown(KeyEvent.VK_S)) {
                ty += vy;
            }
        }

        if ((tx - 11) > 0) {
            if (keyboard.keyDown(KeyEvent.VK_A)) {
                tx -= vx;
            }
        }

        if ((tx + 11) < dim.width) {
            if (keyboard.keyDown(KeyEvent.VK_D)) {
                tx += vx;
            }
        }

    }

    /*
    Name: updateWorld
    Param: N/A
    Desc: Uses the world matrix to rotate and then
    translate based on what the user chose from processInput().
     */
    @Override
    public void updateWorld() {

        //Apply translate to world matrix
        world = Matrix3x3f.rotate(rot);
        world = world.mul(Matrix3x3f.translate(tx, ty));

    }

    /*
    Name: Render
    Param: Graphics g
    Desc: KeyboardObjects own render method, which allows
    the hexagon to be drawn with translations.
     */
    @Override
    public void render(Graphics g) {

        g.setColor(Color.RED);
        Vector2f S = world.mul(polygon[polygon.length - 1]);
        Vector2f P = null;
        for (int i = 0; i < polygon.length; ++i) {
            P = world.mul(polygon[i]);
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
    }
}
