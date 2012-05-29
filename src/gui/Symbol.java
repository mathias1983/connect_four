package gui;

import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public interface Symbol
{
    public void draw( PApplet gui, float x, float y, float width, float height, int transparency);

    public int get_section();
}
