package gui;

import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 * Date: 5/17/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
class Button
{
    int x, y;
    int size;

    boolean over = false;
    boolean pressed = false;
    PApplet gui;
    boolean locked = false;

    Button(  PApplet gui, int x, int y, int size)
    {
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    void update()
    {

    }

    boolean pressed()
    {
        if(over) {
            locked = true;

            return true;
        }
        else {
            locked = false;
            return false;
        }
    }

    boolean over()
    {
        return true;
    }

    boolean overRect(int x, int y, int width, int height)
    {
        if (gui.mouseX >= x && gui.mouseX <= x+width &&
                    gui.mouseY >= y && gui.mouseY <= y+height) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean overCircle(int x, int y, int diameter)
    {
        float disX = x - gui.mouseX;
        float disY = y - gui.mouseY;
        if(gui.sqrt(gui.sq(disX) + gui.sq(disY)) < diameter/2 ) {
            return true;
        }
        else {
            return false;
        }
    }

}
