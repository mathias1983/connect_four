package gui;

import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
class RectButton extends Button
{
    PApplet gui;

    RectButton(PApplet gui, int ix, int iy, int isize)
    {
        super(gui, ix, iy, isize);
        this.gui = gui;
        x = ix;
        y = iy;
        size = isize;

    }

    boolean over()
    {
        if( overRect(x, y, size, size) ) {
            over = true;
            return true;
        }
        else {
            over = false;
            return false;
        }
    }

    void draw()
    {
        gui.stroke(255);
        gui.fill(255,255,255);
        gui.rect(x, y, size, size);
    }
}
