package gui;

import config.GuiConfig;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class RedCircle implements Symbol
{
    int section = 0;

    public RedCircle( int section )
    {
        this.section = section;
    }

    @Override
    public void draw(PApplet gui, float x, float y, float width, float height, int transparency)
    {

        gui.ellipseMode(PApplet.CORNER);

        gui.fill(255,0,0);
        gui.smooth();
        gui.strokeWeight(GuiConfig.STROKE);
        gui.stroke( 255,0,0,transparency );
        gui.ellipse(x,y,width,height);


    }

    @Override
    public int get_section()
    {
        return this.section;
    }
}
