package gui;

import config.GuiConfig;

/**
 * Created with IntelliJ IDEA.
 * User: mathias
 */
public class Coordinate2D
{
    public float mouse_x = 0;
    public float mouse_y = 0;
    public int section = 0;
    public float section_x = 0;
    public float section_y = 0;

    public Coordinate2D(  )
    {

    }

    public Coordinate2D(int section, float mouse_x, float mouse_y, float section_x, float section_y)
    {
        this.section = section;
        this.mouse_x = mouse_x;
        this.mouse_y = mouse_y;
        this.section_x = section_x;
        this.section_y = section_y;
    }

    public Coordinate2D calculate_coordinates2D(float board_x, float board_y, float cell_width, float cell_height )
    {
        int section_counter = 0;
        this.section        =  section_counter;

        for( int i = 1; i <= GuiConfig.BOARD_COLUMNS; ++i )
        {
            float start_y = (board_y + i * cell_height) - cell_height;
            float end_y   = (board_y + i * cell_height);

            for( int j = 1; j <= GuiConfig.BOARD_COLUMNS; ++j )
            {
                float start_x = (board_x + j * cell_width) - cell_width;
                float end_x   = (board_x + j * cell_width);

                if( is_within_coordinate_range( start_x, start_y, end_x, end_y ) )
                {
                    this.section =  section_counter;
                    System.out.println("Feld: "+section_counter);
                    return new Coordinate2D( section_counter, this.mouse_x, this.mouse_y, start_x, start_y );
                }
                ++section_counter;
            }
        }

        // wenn außerhalb der grenzen geklickt, dann Koordinaten für erstes feld zurückgeben
        float start_x = (board_x +  cell_width) - cell_width;
        float start_y = (board_y +  cell_height) - cell_height;
        return new Coordinate2D( 0, this.mouse_x, this.mouse_y, start_x, start_y );
    }

    public Coordinate2D calculate_coordinates2D_from_section(float board_x, float board_y, float cell_width, float cell_height, int section )
    {
        int section_counter = 0;
        this.section        =  section_counter;

        for( int i = 1; i <= GuiConfig.BOARD_COLUMNS; ++i )
        {
            float start_y = (board_y + i * cell_height) - cell_height;
            float end_y   = (board_y + i * cell_height);

            for( int j = 1; j <= GuiConfig.BOARD_COLUMNS; ++j )
            {
                float start_x = (board_x + j * cell_width) - cell_width;
                float end_x   = (board_x + j * cell_width);

                if( section_counter == section )
                {
                    return new Coordinate2D( section_counter, 0, 0, start_x, start_y );
                }
                ++section_counter;
            }
        }

        // wenn außerhalb der grenzen geklickt, dann Koordinaten für erstes feld zurückgeben
        float start_x = (board_x +  cell_width) - cell_width;
        float start_y = (board_y +  cell_height) - cell_height;
        return new Coordinate2D( 0, this.mouse_x, this.mouse_y, start_x, start_y );
    }


    private boolean is_within_coordinate_range( float start_x, float start_y, float end_x, float end_y )
    {
        if( this.mouse_x >= start_x && this.mouse_x < end_x )
        {
            if( this.mouse_y >= start_y && this.mouse_y < end_y )
            {
                return true;
            }
        }
        return false;
    }
}
