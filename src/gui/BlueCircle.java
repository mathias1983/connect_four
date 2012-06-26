package gui;

import config.GuiConfig;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class BlueCircle implements Symbol
{
    int section = 0;

    public BlueCircle( int section )
    {
        this.section = section;
    }

    @Override
    public void draw(PApplet gui, float x, float y, float width, float height, int transparency)
    {

        gui.ellipseMode(PApplet.CORNER);

        gui.fill(0,0,255);
        gui.smooth();
        gui.strokeWeight(GuiConfig.STROKE);
        gui.stroke( 0,0,255,transparency );
        gui.ellipse(x,y,width,height);


    }

    @Override
    public int get_section()
    {
        return this.section;
    }
}
