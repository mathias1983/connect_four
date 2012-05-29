package gui;

import config.GuiConfig;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class Cross implements Symbol
{
    int section = 0;

    public Cross( int section )
    {
        this.section = section;
    }

    @Override
    public void draw(PApplet gui, float x, float y, float width, float height, int transparency)
    {

        gui.fill(255,255,255);
        gui.smooth();
        gui.strokeWeight(GuiConfig.STROKE);
        gui.stroke( 0,0,255,transparency );
        gui.line( x, y, x+width, y+height );
        gui.line( x+width, y, x, y+height );





        /*
        gui.pushMatrix();
        gui.translate( x, y );
        gui.rotate(PApplet.radians(45));
        gui.translate( -x, -y );
        gui.rect( x + height/5, y + height/10, width, height/10 );
        gui.popMatrix();
          */

    }

    @Override
    public int get_section()
    {
        return this.section;
    }
}
