package controller;


import config.GuiConfig;
import gui.*;
import processing.core.PApplet;
import java.util.LinkedList;

import minimax.Minimax;
import minimax.State;
import minimax.Minimax.Player;


public class Main extends PApplet
{
    private Environment env;
    private final static int BOARD_COLUMNS = GuiConfig.BOARD_COLUMNS;
    private final static int BOARD_ROWS    = GuiConfig.BOARD_ROWS;

    LinkedList<Symbol> results;
    State state;

    public static void main(String[] args)
    {
        PApplet.main(new String[]{ "controller.Main"});	
    }

    public void setup()
    {
        size(GuiConfig.SCREEN_WIDTH, GuiConfig.SCREEN_HEIGHT);
        this.screenWidth  = GuiConfig.SCREEN_WIDTH;
        this.screenHeight = GuiConfig.SCREEN_HEIGHT;
        background(100, 100, 100);
        env = new Environment(this);
        results = new LinkedList<Symbol>();
        state = new State();
    }

    public void draw()
    {
        env.draw();
    }

    public void mouseClicked()
    {
        //Minimax mmPlayerMax = new Minimax(Player.MAX, BOARD_SIZE);
		Minimax mmPlayerMin = new Minimax(Player.MIN);
    	State prevState = state.deepCopy(); //new State();
    	
//    	state.field[0][1] = 1; 
//    	results.add(stateToSymbol(prevState, state));
//    	env.get_board().set_results( results );
//    	prevState = state.deepCopy();	
//    	state.field[1][2] = 1;
//    	results.add(stateToSymbol(prevState, state));
//    	env.get_board().set_results( results );
//    	prevState = state.deepCopy();
//    	state.field[2][5] = 1; 
//    	results.add(stateToSymbol(prevState, state));
//    	env.get_board().set_results( results );
//    	prevState = state.deepCopy();
//    	state.field[5][6] = -1; 
//    	results.add(stateToSymbol(prevState, state));
//    	env.get_board().set_results( results );
//    	prevState = state.deepCopy();
//    	
//    	if(state.field[0][1] == 1)
//    		return;
    	
        Coordinate2D co = env.get_board().get_clicked_coordinates();
        System.out.println("Section: " + co.section);
        int field = co.section;
        int x = (field) % BOARD_COLUMNS;
        int y = (field - x) / BOARD_COLUMNS;

        System.out.println(x+" "+y);

        // Testzüge!!!!!!!!!!!!!!!!!!!

        // Spielstein immer jeweils unten im Board einfügen, auch wenn click weiter oben erfolgte
        int inserted = state.rule_insert( x );

        if( inserted != -1 && !mmPlayerMin.terminalTest(prevState, 0))
        {
            // do stuff
            int pos = inserted*BOARD_COLUMNS + x;
        	results.add( stateToSymbol(prevState, state)) ; //new RedCircle(pos) );
        	env.get_board().set_results( results );
        	System.out.println(state.toString());
        	
        	state = mmPlayerMin.getMinimaxDecision(state);
        	results.add(stateToSymbol(prevState, state));
        	env.get_board().set_results( results );
        	System.out.println(state.toString());
        }
    }
    
    private Symbol stateToSymbol(State prevState, State currState)
    {
    	for(int i=0; i<BOARD_ROWS; i++)
    	{
    		for(int j=0; j<BOARD_COLUMNS; j++)
    		{
    			if(prevState.field[i][j] != currState.field[i][j])
    			{
    				if(currState.field[i][j] == 1)
    					return new RedCircle(i*BOARD_COLUMNS+j);
    				else
    					return new Cross(i*BOARD_COLUMNS+j);
    			}
    		}
    	}
    	return null;
    }  
}