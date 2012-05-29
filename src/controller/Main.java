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
    private final static int BOARD_SIZE = GuiConfig.BOARD_COLUMNS;

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
    }

    public void draw()
    {
        env.draw();
    }

    public void mouseClicked()
    {
        // testliste mit vordefinierten zuegen!!
        LinkedList<Symbol> results = new LinkedList<Symbol>();
        Minimax mmPlayerMax = new Minimax(Player.MAX, BOARD_SIZE);
		Minimax mmPlayerMin = new Minimax(Player.MIN, BOARD_SIZE);
    	State prevState = new State();
    	
        Coordinate2D co = env.get_board().get_clicked_coordinates();
        System.out.println("Section: " + co.section);
        int field = co.section;
        int x = (field) % BOARD_SIZE;
        int y = (field - x) / BOARD_SIZE;
         
		prevState.field[y][x] = 1;	

    	State currState = prevState.deepCopy();
    	
		results.add(new Cross(field));

		System.out.println(prevState.toString());
		for (int i=0; i<BOARD_SIZE*BOARD_SIZE-1; i++)
		{
			if(mmPlayerMax.terminalTest(currState) || mmPlayerMin.terminalTest(currState))
				break;
			
			if (i%2 == 0) 
 				currState = mmPlayerMin.getMinimaxDecision(prevState);
			else 
				currState = mmPlayerMax.getMinimaxDecision(prevState);
		
			results.add(this.stateToSymbol(prevState, currState));
			
			prevState = currState.deepCopy();
			System.out.println(prevState.toString());
		}




        env.get_board().set_results( results );
    }
    
    private Symbol stateToSymbol(State prevState, State currState)
    {
    	for(int i=0; i<BOARD_SIZE; i++)
    	{
    		for(int j=0; j<BOARD_SIZE; j++)
    		{
    			if(prevState.field[i][j] != currState.field[i][j])
    			{
    				if(currState.field[i][j] == 1)
    					return new Cross(i*BOARD_SIZE+j);
    				else
    					return new Circle(i*BOARD_SIZE+j);
    			}
    		}
    	}
    	return null;
    }  
}