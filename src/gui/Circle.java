package gui;

import config.GuiConfig;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class Circle implements Symbol
{
    private int section = 0;

    public Circle( int section )
    {
        this.section = section;
    }

    @Override
    public void draw(PApplet gui, float x, float y, float width, float height, int transparency)
    {

        gui.ellipseMode(PApplet.CORNER);

        int offset = 5;
        gui.strokeWeight(GuiConfig.STROKE-5);
        gui.stroke( 200,200,200,transparency );
        gui.ellipse(x+offset,y+offset,width,height);


        gui.fill(255,255,255);
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
