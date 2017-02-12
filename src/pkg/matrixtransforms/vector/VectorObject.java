package pkg.matrixtransforms.vector;

import java.awt.Dimension;
import java.awt.Graphics;
import pkg.matrixtransforms.input.KeyboardInput;
import pkg.matrixtransforms.input.MouseInput;

public abstract class VectorObject implements Drawable {

    protected Vector2f[] polygon;        //Objects defined points
    protected Matrix3x3f world;          //Objects transformation matrix
    protected Dimension dim;             //Canvas dimensions
    protected float tx;                  //Translation position x
    protected float ty;                  //Translation position y
    protected MouseInput mouse;          //Mouse input
    protected KeyboardInput keyboard;    //Keyboard input
    protected float rot;                 //Rotation
    protected float rotStep;             //Rotation Steps

    public VectorObject(Dimension dim, MouseInput mouse, KeyboardInput keyboard) {
        this.dim = dim;
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

    public abstract void processInput();

    @Override
    public abstract void updateWorld();

    @Override
    public abstract void render(Graphics g);

}
