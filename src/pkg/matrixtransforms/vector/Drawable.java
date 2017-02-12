package pkg.matrixtransforms.vector;

import java.awt.Graphics;

interface Drawable {

    void updateWorld(); //Update the World Matrix

    void render(Graphics g); //Draw the object with passed Graphics

}
