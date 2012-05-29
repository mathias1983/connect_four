package gui;

import processing.core.PApplet;

public class Environment
{
    private PApplet gui;
    private Board   board;

    public Environment( PApplet applet )
    {
        this.gui   = applet;
        this.setup();
        this.board = new Board2D(gui);
    }

    public void setup()
    {

    }

    public void set_board( Board bord )
    {
        this.board = bord;
    }

    public Board get_board()
    {
        return this.board;
    }

    public void draw()
    {
        this.board.draw();
    }


}
