package gui;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public interface Board
{
    public void draw();

    public Coordinate2D get_clicked_coordinates();

    void set_results( LinkedList<Symbol> results);
}
