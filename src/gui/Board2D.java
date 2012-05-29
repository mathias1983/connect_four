package gui;

import processing.core.PApplet;
import config.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class Board2D implements Board
{
    private PApplet gui;
    private Coordinate2D mouse_coordinates;
    private float board_x;
    private float board_y;
    private float cell_width;
    private float cell_height;

    private LinkedList<Symbol> results          = new LinkedList<Symbol>();
    private LinkedList<Symbol> results_animated = new LinkedList<Symbol>();


    private int step_counter = 0;
    private int transparency = 0;

    public Board2D( PApplet gui)
    {
        this.gui = gui;
        this.mouse_coordinates = new Coordinate2D(  );
        board_x     =  (GuiConfig.SCREEN_WIDTH/2)  - (GuiConfig.BOARD_WIDTH/2);
        board_y     =  (GuiConfig.SCREEN_HEIGHT/2) - (GuiConfig.BOARD_HEIGHT/2);
        cell_width  =   GuiConfig.BOARD_WIDTH  / GuiConfig.BOARD_COLUMNS;
        cell_height =   GuiConfig.BOARD_HEIGHT / GuiConfig.BOARD_COLUMNS;
    }

    public void set_results( LinkedList<Symbol> results  )
    {
       this.results = results;
       this.results_animated = new LinkedList<Symbol>();
    }

    @Override
    public void draw()
    {
        gui.smooth();
        gui.strokeWeight(2);
        gui.stroke(100, 100, 100);
        this.draw_plane(   board_x, board_y );
        this.draw_columns( board_x, board_y );
        this.draw_rows(    board_x, board_y );
        this.draw_symbols();
    }

    private void draw_symbols()
    {
        this.fill_results_animated();

        Coordinate2D co = new Coordinate2D();
        int margin = GuiConfig.MARGIN;

        Iterator<Symbol> it = this.results_animated.iterator();

        int size_counter = 1;

        while( it.hasNext() )
        {
            Symbol sym = it.next();
            Coordinate2D temp = co.calculate_coordinates2D_from_section(this.board_x, this.board_y, this.cell_width, this.cell_height, sym.get_section() );
                // aktuell animierter zug
            if( size_counter == results_animated.size() )
            {
                sym.draw(this.gui, temp.section_x + margin, temp.section_y + margin, this.cell_width - (margin * 2), this.cell_height - (margin * 2),transparency);
                this.calculate_transparency_bounds();
            }
            else
            {       // alte züge
                sym.draw(this.gui, temp.section_x + margin, temp.section_y + margin, this.cell_width - (margin * 2), this.cell_height - (margin * 2),255);
            }


            ++size_counter;
        }
    }

    private void calculate_transparency_bounds()
    {
        int speed = GuiConfig.TRANSPARENCY_SPEED;
        if( speed > 30 ) speed = 30;
        if( speed < 5 )  speed = 5;
        if( this.transparency >= 255 )
            this.transparency = 255;
        this.transparency += speed;
    }

    private void fill_results_animated()
    {
        ++step_counter;

        if( step_counter % this.get_animation_speed() == 0 )
        {
            if( results.size() > 0 )
            {
                results_animated.add( results.removeFirst() );
                transparency = 0;
            }
        }
        if( step_counter >= 255 )
            step_counter = 0;
    }

    private int get_animation_speed()
    {
        int speed = 100 - GuiConfig.SPEED;
        if( speed < 10 )  speed = 10;
        if( speed > 100 ) speed = 100;
        return speed;
    }

    @Override
    public Coordinate2D get_clicked_coordinates(  )
    {
        this.mouse_coordinates.mouse_x = gui.mouseX;
        this.mouse_coordinates.mouse_y = gui.mouseY;
        Coordinate2D co = mouse_coordinates.calculate_coordinates2D(board_x, board_y, cell_width, cell_height);
        return co;
    }

    private void draw_rows( float board_x, float board_y  )
    {
        for( int i = 1; i <= GuiConfig.BOARD_COLUMNS; ++i )
        {
            float row_x1  = board_x;
            float row_x2  = board_x + GuiConfig.BOARD_WIDTH;
            float row_y   = board_y + i * (GuiConfig.BOARD_HEIGHT / GuiConfig.BOARD_COLUMNS);
            gui.fill(0,255,255);
            gui.line( row_x1, row_y, row_x2, row_y  );
        }
    }

    private void draw_plane( float board_x, float board_y  )
    {
        gui.fill(255,255,255);
        gui.rect( board_x , board_y,GuiConfig.BOARD_WIDTH, GuiConfig.BOARD_HEIGHT );
    }

    private void draw_columns( float board_x, float board_y  )
    {
        for( int i = 1; i <= GuiConfig.BOARD_COLUMNS; ++i )
        {
            float column_x  = board_x + i * (GuiConfig.BOARD_WIDTH / GuiConfig.BOARD_COLUMNS);
            float column_y1 = board_y;
            float column_y2 = board_y + GuiConfig.BOARD_HEIGHT;
            gui.fill(0,255,255);
            gui.line( column_x, column_y1, column_x, column_y2  );
        }
    }
}
