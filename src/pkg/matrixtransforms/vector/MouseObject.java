package pkg.matrixtransforms.vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import pkg.matrixtransforms.input.KeyboardInput;
import pkg.matrixtransforms.input.MouseInput;

public class MouseObject extends VectorObject {

    public MouseObject(Dimension dim, MouseInput mouse, KeyboardInput keyboard) {
        super(dim, mouse, keyboard);
        //LOL Test
        initialize();

    }

    /*
    Name: initialize
    Param: N/A
    Desc: Sets default mouse to middle of screen just in case. 
    Initalizes the triangle object as well at rotation values.
     */
    private void initialize() {

        this.tx = dim.width / 2;
        this.ty = dim.height / 2;

        //Triangle
        this.polygon = new Vector2f[]{new Vector2f(0, -10), new Vector2f(-9, 5), new Vector2f(9, 5)};

        //Rotation initialize
        this.rot = 0.0f;
        this.rotStep = (float) Math.toRadians(1);
    }

    /*
    Name: ProcessInput
    Param: N/A
    Desc: Processes mouse input, and controls rotation of the
    triangle, left mouse for counter clockwise, right mouse for 
    clockwise.
     */
    @Override
    public void processInput() {

        //Rotate
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            this.rot -= rotStep;
        }

        if (mouse.buttonDown(MouseEvent.BUTTON3)) {
            this.rot += rotStep;
        }

        //Translations
        this.tx = mouse.getPosition().x;
        this.ty = mouse.getPosition().y;

    }

    /*
    Name: updateWorld
    Param: N/A
    Desc: Applies the world matrix transformations to the
    triangle based on process input.
     */
    @Override
    public void updateWorld() {

        //Apply translate to world matrix
        world = Matrix3x3f.rotate(rot);
        world = world.mul(Matrix3x3f.translate(tx, ty));

    }

    /*
    Name: render
    Param: Graphics g
    Desc: Renders the triangle with appropiate translated values.
     */
    @Override
    public void render(Graphics g) {

        g.setColor(Color.BLUE);
        Vector2f S = world.mul(polygon[polygon.length - 1]);
        Vector2f P = null;
        for (int i = 0; i < polygon.length; ++i) {
            P = world.mul(polygon[i]);
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
    }
}
