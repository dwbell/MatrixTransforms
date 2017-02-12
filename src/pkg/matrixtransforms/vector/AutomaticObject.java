package pkg.matrixtransforms.vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import pkg.matrixtransforms.input.KeyboardInput;
import pkg.matrixtransforms.input.MouseInput;

public class AutomaticObject extends VectorObject {

    private final int a = 10;       //Square positive size
    private final int b = -10;      //Square negative size
    private float vx = 1.4f;        //Rate of change x
    private float vy = 1.4f;        //Rate of change y

    public AutomaticObject(Dimension dim, MouseInput mouse, KeyboardInput keyboard) {
        super(dim, mouse, keyboard);

        initialize();
    }

    /*
    Name: initialize
    Param: N/A
    Desc: Sets up the square vector object, as well as
    the world matrix, and initializes its start point to 
    center of screen.
     */
    private void initialize() {

        //Setting square points
        this.polygon = new Vector2f[]{new Vector2f(a, b), new Vector2f(b, b),
            new Vector2f(b, a), new Vector2f(a, a),};

        //World matrix for each point
        this.world = new Matrix3x3f();

        //Set to object to center of screen
        this.tx = dim.width / 2;
        this.ty = dim.height / 2;
    }

    /*
    Name: processInput 
    Param: N/A
    Desc: Does nothing, because automaticObject does not
    need user input.
     */
    @Override
    public void processInput() {
        //Nothing
    }

    /*
    Name: updateWorld
    Param: N/A
    Desc: Updates the world matrix to account for 
    translations and screen bounds checking. This 
    method gives the square a "bouncy" effect.
     */
    @Override
    public void updateWorld() {

        //Bounds checking each corner and updating translation
        tx += vx;
        if ((tx - a) < 0 || (tx + a) > dim.width) {
            vx = -vx;
        }
        ty += vy;
        if ((ty - a) < 0 || (ty + a) > dim.height) {
            vy = -vy;
        }

        //Apply translate to world matrix
        world = Matrix3x3f.translate(tx, ty);
    }

    /*
    Name: render
    Param: Graphics g
    Desc: AutomaticObjects own render method to draw the 
    square to screen and watch it move. 
     */
    @Override
    public void render(Graphics g) {

        g.setColor(Color.GREEN);
        Vector2f S = world.mul(polygon[polygon.length - 1]);
        Vector2f P = null;
        for (int i = 0; i < polygon.length; ++i) {
            P = world.mul(polygon[i]);
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            //g.fillRect((int) tx - a, (int) ty - a, (int) a * 2, (int) a * 2);
            S = P;
        }
    }
}
