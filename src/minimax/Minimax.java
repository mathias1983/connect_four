package minimax;

import config.GuiConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Minimax 
{	
	public enum Player 
	{MIN, MAX};

    private static final int POS_INFINITY = (int)Double.POSITIVE_INFINITY;
    private static final int NEG_INFINITY = (int)Double.NEGATIVE_INFINITY;
    
    private static final int MAX_DEPTH = 10;
    private long MAX_MILLISECONDS = 1000;
    private long t1;
    private int currentDepth;
	
	Player player = Player.MAX;
	public static int numberToWin = 4;

	public Minimax(Player p)
	{
		this.player = p;
	}
	
	public State getMinimaxDecision(State initialState) 
	{
		System.out.println("player: " + player);
		t1 = System.currentTimeMillis();
		Action bestAction = null;
		double bestUtility = Double.NEGATIVE_INFINITY;
		
		List<Action> actionList = getActions(initialState);
		Collections.shuffle(actionList);
		
		for (Action action : actionList) 
		{
			double utility = minValue(getResult(initialState, action), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
			if (utility > bestUtility) 
			{
				bestUtility = utility;
				bestAction = action.deepCopy();
			}
		}

		State resultState = getResult(initialState, bestAction);
		
		long t2 = System.currentTimeMillis(); 
		System.out.println("TIME: " + (t2-t1) + "ms");
		
		return resultState;		
	}
	
	private double minValue(State state, double alpha, double beta, int depth) 
	{
		depth++;
		
		double utility = Double.POSITIVE_INFINITY;
		
		if (terminalTest(state, depth))
		{
//			System.out.println("terminal: ");
//			System.out.println(state.toString());
//			System.out.println("");
			return pruned_utility(state);
		}
		
		List<Action> actionList = getActions(state);
		Collections.shuffle(actionList);
		
		for (Action action: actionList) 
		{
			double tmp = maxValue(getResult(state, action), alpha, beta, depth);
			utility = Math.min(utility,tmp);
			
			if(utility <= alpha)
				return utility;
			
			beta = Math.min(beta, utility);
		}
		return utility;
	}
	
	private double maxValue(State state, double alpha, double beta, int depth) 
	{
		depth++;
		
		double utility = Double.NEGATIVE_INFINITY;
		
		if (terminalTest(state, depth))
		{
//			System.out.println("terminal: ");
//			System.out.println(state.toString());
//			System.out.println("");
			return pruned_utility(state);
		}

		List<Action> actionList = getActions(state);
		Collections.shuffle(actionList);
		
		for (Action action: actionList) 
		{
			double tmp = minValue(getResult(state,action), alpha, beta, depth);
			utility = Math.max(utility, tmp);
			
			if(utility >= beta)
				return utility;
			
			alpha = Math.max(alpha, utility);
		}
		return utility;
	}

	private State getResult(State currentState, Action action) 
	{
		State resultState = currentState.deepCopy(); 

		if (action.player == Player.MAX) 
			resultState.field[action.row][action.col] = 1;
		else 
			resultState.field[action.row][action.col] = -1;
		
//		System.out.println("CURRENT RESULT: ");
//		System.out.println(resultState.toString());
//		System.out.println("");
	
		return resultState;
	}

    /***
     * Findet die naechsten moeglichen Spielzuege und legt fest, welcher Spieler
     * aktuell am Zug ist.
     * Gueltige Spielzuege sind leere Spielfelder die entweder ganz unten im Spielfeld sind
     * oder leere Felder die sich ueber anderen Spielsteinen befinden.
     * @param state
     * @return
     */
	private List<Action> getActions(State state) 
	{
		List<Action> actions = new ArrayList<Action>();
		int moves = 0;

        int columns = GuiConfig.BOARD_COLUMNS;
        int rows    = GuiConfig.BOARD_ROWS;
        int row_count = rows - 1;

		for (int i=0; i<rows; i++)
			for (int j =0; j<columns; j++)
				if(state.field[i][j] != 0)
					moves++;
		
		Player player = Player.MAX;
		if(moves % 2 == 1)
			player = Player.MIN;

        // jeweils eine Reihe absuchen
		for (int i=0; i<columns; i++)
		{
            // ganz unten im spielbrett(hoechster y wert) zuerst nach leerem Feld suchen
            // wenn keines gefunden ein feld darueber suchen
			while( row_count > -1 )
            {
                if (state.field[row_count][i] == 0)
                {
                    Action action = new Action();
                    action.col = i;
                    action.row = row_count;
                    action.player = player;
                    actions.add(action);
                    break;
                }
                // ein feld hoeher gehen
                --row_count;
            }
            row_count = rows - 1;

		}
		return actions;
	}

    private int is_row(int[][] field, Player player_, int x, int y, int dx, int dy) {
        int num = 0;
        int player = -1;
        if( player_ == Player.MAX )
            player = 1;

        // 4 freie oder von spieler belegte Felder in Richtung (dx,dy)?
        if (  ((field[y][x]==player) || (field[y][x]==0))
                      && ((field[y+1*dy][x+1*dx]==player) || (field[y+1*dy][x+1*dx]==0))
                      && ((field[y+2*dy][x+2*dx]==player) || (field[y+2*dy][x+2*dx]==0))
                      && ((field[y+3*dy][x+3*dx]==player) || (field[y+3*dy][x+3*dx]==0))) {

            // zaehle Anzahl von spieler belegter Felder
            for (int i=0; i<4; i++)
                if (field[y+i*dy][x+i*dx]==player)
                    num+=player; //++;
        }
        return num;
    }
    
    private int utility(State state)
    {    	
		int val = 0;
		
//		val = state.getVerticalScore(3, 0, 4);
//		val = state.getDiagonalScore(0, 3, 2, -1);
//		System.out.println("val: " + val);
		
		for (int i=0; i<numberToWin; i++) 
			val += state.field[i][i];
	
		if (val == numberToWin && player == Player.MAX || val == -numberToWin && player == Player.MIN) 
            return 1;
    
		if ( val == numberToWin && player == Player.MIN || val == -numberToWin && player == Player.MAX) 
            return -1;
		
		val=0;
		for (int i=0; i<numberToWin; i++) 
			val += state.field[i][numberToWin-i-1];

		if (val == numberToWin && player == Player.MAX || val == -numberToWin && player == Player.MIN) 
            return 1;
    
		if ( val == numberToWin && player == Player.MIN || val == -numberToWin && player == Player.MAX) 
            return -1;

		for (int i=0; i<numberToWin; i++) 
		{
			val = 0;
			for (int j=0; j<numberToWin; j++) 
				val += state.field[i][j];

			if (val == numberToWin && player == Player.MAX || val == -numberToWin && player == Player.MIN) 
	            return 1;
	    
			if ( val == numberToWin && player == Player.MIN || val == -numberToWin && player == Player.MAX) 
	            return -1;
		}

		for (int j=0; j<numberToWin; j++) 
		{
			val = 0;
			for (int i=0; i<numberToWin; i++) 
				val += state.field[i][j];

			if (val == numberToWin && player == Player.MAX || val == -numberToWin && player == Player.MIN) 
	            return 1;
	    
			if ( val == numberToWin && player == Player.MIN || val == -numberToWin && player == Player.MAX) 
	            return -1;
		}
		return 0;
    }
    
    private int getRowScore(int[][] field, Player player_, int x, int y, int dx, int dy) {
        int num = 0;
        int player = 1;
        if( player_ == Player.MAX )
            player = -1;

        // 4 freie oder von spieler belegte Felder in Richtung (dx,dy)?
        if (  			 ((field[y][x]==player) 		  || (field[y][x]==0))
                      && ((field[y+1*dy][x+1*dx]==player) || (field[y+1*dy][x+1*dx]==0))
                      && ((field[y+2*dy][x+2*dx]==player) || (field[y+2*dy][x+2*dx]==0))
                      && ((field[y+3*dy][x+3*dx]==player) || (field[y+3*dy][x+3*dx]==0))) {

            // zaehle Anzahl von spieler belegter Felder
            for (int i=0; i<4; i++)
                if (field[y+i*dy][x+i*dx]==player)
                    num += player;
        }
        return num;
    }
    
    private int pruned_utility( State state )
    {   	
        int[][] field = state.field;
        int columns = GuiConfig.BOARD_COLUMNS;
        int rows    = GuiConfig.BOARD_ROWS;

        int min2 = 0; int max2 = 0;
        int min3 = 0; int max3 = 0;

        for (int x=0; x<columns; x++) 
        {
            for (int y=0; y<rows; y++) 
            {
                // Noch 4 Chips nach oben moeglich?
                if (rows-y>=4) 
                {
                    // 4 gleiche Chips?
                    if (getRowScore(field, Player.MAX, x, y, 0, 1)==4 || getRowScore(field, Player.MIN, x, y, 0, 1)== -4)
                        return POS_INFINITY;  // gewonnen
                    
                    if (getRowScore(field, Player.MIN, x, y, 0, 1)==4 || getRowScore(field, Player.MAX, x, y, 0, 1)== -4)
                        return NEG_INFINITY;  // verloren
                    
                    // 3 gleiche Chips?
                    if (getRowScore(field, Player.MAX, x, y, 0, 1)==3 || getRowScore(field, Player.MIN, x, y, 0, 1)== -3)
                        max3++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 0, 1)==3 || getRowScore(field, Player.MAX, x, y, 0, 1)== -3)
                        min3--;               
                    
                    // 2 gleiche Chips?
                    if (getRowScore(field, Player.MAX, x, y, 0, 1)==2 || getRowScore(field, Player.MIN, x, y, 0, 1)== -2)
                        max2++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 0, 1)==2 || getRowScore(field, Player.MAX, x, y, 0, 1)== -2)
                        min2--;
                }
                
                // Noch 4 Chips nach rechts oben moeglich?
                if ((rows-y>=4) && (columns-x>=4)) 
                {
                    // 4 gleiche Chips nach rechts oben?
                    if (getRowScore(field, Player.MAX, x, y, 1, 1)==4 || getRowScore(field, Player.MIN, x, y, 1, 1)== -4)
                        return POS_INFINITY;  // gewonnen
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 1)==4 || getRowScore(field, Player.MAX, x, y, 1, 1)== -4)
                        return NEG_INFINITY;  // verloren
                    
                    // 3 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, 1)==3 || getRowScore(field, Player.MIN, x, y, 1, 1)== -3)
                        max3++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 1)==3 || getRowScore(field, Player.MAX, x, y, 1, 1)== -3)
                        min3--;

                    // 2 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, 1)==2 || getRowScore(field, Player.MIN, x, y, 1, 1)== -2)
                        max2++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 1)==2 || getRowScore(field, Player.MAX, x, y, 1, 1)== -2)
                        min2--;
                }
                
                // Noch 4 Chips nach rechts moeglich?
                if (columns-x>=4) 
                {
                    if (getRowScore(field, Player.MAX, x, y, 1, 0)==4 || getRowScore(field, Player.MIN, x, y, 1, 0)== -4)
                        return POS_INFINITY;  // gewonnen
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 0)==4 || getRowScore(field, Player.MAX, x, y, 1, 0)== -4)
                        return NEG_INFINITY;  // verloren	
        			
                    // 3 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, 0)==3 || getRowScore(field, Player.MIN, x, y, 1, 0)== -3)
                        max3++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 0)==3 || getRowScore(field, Player.MAX, x, y, 1, 0)== -3)
                        min3--;

                    // 2 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, 0)==2 || getRowScore(field, Player.MIN, x, y, 1, 0)== -2)
                        max2++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, 0)==2 || getRowScore(field, Player.MAX, x, y, 1, 0)== -2)
                        min2--;
                }
                
                // Noch 4 Chips nach rechts unten moeglich?
                if ((columns-x>=4) && (y>=3)) 
                {
                    if (getRowScore(field, Player.MAX, x, y, 1, -1)==4 || getRowScore(field, Player.MIN, x, y, 1, -1)== -4)
                        return POS_INFINITY;  // gewonnen
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, -1)==4 || getRowScore(field, Player.MAX, x, y, 1, -1)== -4)
                        return NEG_INFINITY;  // verloren	
                	
                    // 3 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, -1)==3 || getRowScore(field, Player.MIN, x, y, 1, -1)== -3)
                        max3++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, -1)==3 || getRowScore(field, Player.MAX, x, y, 1, -1)== -3)
                        min3--;	
                    
                    // 2 gleiche Chips uebereinander?
                    if (getRowScore(field, Player.MAX, x, y, 1, -1)==2 || getRowScore(field, Player.MIN, x, y, 1, -1)== -2)
                        max2++;
                    
                    if (getRowScore(field, Player.MIN, x, y, 1, -1)==2 || getRowScore(field, Player.MAX, x, y, 1, -1)== -2)
                        min2--;	
                }
            }
        }

        int result = 100*max3 + 10*max2 + 200*min3 + 10*min2;
        
//        if (result>0 && player == Player.MAX  || result<0 && player == Player.MIN )
//            return Math.abs(result);
//        
//        if (result>0 && player == Player.MIN)
//        	return -1*result;
        
        System.out.println("State: "); 
        System.out.println("" + state.toString());
        System.out.println("max3:" + max3 + "  max2:" + max2 + "  min3:" + min3 + "  min2:" + min2);
        System.out.println("utility: " + result + " depth: " + currentDepth);
        System.out.println("");
        System.out.println("");
        return result;
    }

    private int pruned_utility_old( State state )
    {   	
        int[][] field = state.field;
        int columns = GuiConfig.BOARD_COLUMNS;
        int rows    = GuiConfig.BOARD_ROWS;


        int min2 = 0; int max2 = 0;
        int min3 = 0; int max3 = 0;

        for (int x=0; x<columns; x++) {
            for (int y=0; y<rows; y++) {
                // Noch 4 Chips nach oben moeglich?
                if (rows-y>=4) {
                    // 4 gleiche Chips?
                    if (is_row(field, Player.MAX, x, y, 0, 1)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==-4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips?
                    else if (is_row(field, Player.MAX, x, y, 0, 1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==-3)
                        min3--;
                        // 2 gleiche Chips?
                    else if (is_row(field, Player.MAX, x, y, 0, 1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==-2)
                        min2--;
                }
                // Noch 4 Chips nach rechts oben moeglich?
                if ((rows-y>=4) && (columns-x>=4)) {
                    // 4 gleiche Chips nach rechts oben?
                    if (is_row(field, Player.MAX, x, y, 1, 1)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==-4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==-3)
                        min3--;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==-2)
                        min2--;
                }
                // Noch 4 Chips nach rechts moeglich?
                if (columns-x>=4) {
                    if (is_row(field, Player.MAX, x, y, 1, 0)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==-4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 0)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==-3)
                        min3--;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 0)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==-2)
                        min2--;
                }
                // Noch 4 Chips nach rechts unten moeglich?
                if ((columns-x>=4) && (y>=3)) {
                    if (is_row(field, Player.MAX, x, y, 1, -1)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==-4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, -1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==-3)
                        min3--;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, -1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==-2)
                        min2--;
                }
            }
        }

        int result = 10*max2 + 100*max3 + 10*min2 + 100*min3;
        
//        if (result>0 && player == Player.MAX  || result<0 && player == Player.MIN )
//            result = Math.abs(result);
//        
//        if (result>0 && player == Player.MIN)
//        	result = -1*result;
        
//        System.out.println("State: "); 
//        System.out.println("" + state.toString());
//        System.out.println("max3:" + max3 + "  max2:" + max2 + "  min3:" + min3 + "  min2:" + min2);
//        System.out.println("utility: " + result + " depth: " + currentDepth);
//        System.out.println("");
//        System.out.println("");
        return 0; //result;
    }

	
	public boolean terminalTest(State state, int depth) 
	{
		currentDepth = depth;
		if(depth > MAX_DEPTH)
			return true;
		
		long t2 = System.currentTimeMillis(); 
		if((t1!=0) && (t2-t1) >= MAX_MILLISECONDS)
		{
			System.out.println("time limit exceeded " + (t2-t1));
			return true;
		}

		int playedFields = 0;
        int columns = GuiConfig.BOARD_COLUMNS;
        int rows    = GuiConfig.BOARD_ROWS;

        // besetzte felder zaehlen
		for (int i=0; i<rows; i++)
		{
			int totalRow = 0;
			for (int j=0; j<columns; j++)
			{
				if (state.field[i][j] != 0)
					playedFields++;
				
				totalRow += state.field[i][j];
			}
			if (Math.abs(totalRow) == rows)
				return true;
		}
		// spielfeld voll belegt
		if (playedFields == rows*columns)
			return true;

        // 4er reihe gewinnbedingung
        for (int x=0; x<columns; x++) {
            for (int y=0; y<rows; y++) {
                // Noch 4 Chips nach oben moeglich?
                if (rows-y>=4) {
                    // 4 gleiche Chips?
                    // 4 gleiche Chips?
                    if (getRowScore(state.field, Player.MAX, x, y, 0, 1)==4 || getRowScore(state.field, Player.MIN, x, y, 0, 1)== -4)
                        return true;  // gewonnen

                    if (getRowScore(state.field, Player.MIN, x, y, 0, 1)==4 || getRowScore(state.field, Player.MAX, x, y, 0, 1)== -4)
                        return true;  // verloren
                }
                if ((rows-y>=4) && (columns-x>=4)) {
                    // 4 gleiche Chips nach rechts oben?
                    if (getRowScore(state.field, Player.MAX, x, y, 1, 1)==4 || getRowScore(state.field, Player.MIN, x, y, 1, 1)== -4)
                        return true;  // gewonnen

                    if (getRowScore(state.field, Player.MIN, x, y, 1, 1)==4 || getRowScore(state.field, Player.MAX, x, y, 1, 1)== -4)
                        return true;  // verloren
                }
                if (columns-x>=4) {
                    if (getRowScore(state.field, Player.MAX, x, y, 1, 0)==4 || getRowScore(state.field, Player.MIN, x, y, 1, 0)== -4)
                        return true;  // gewonnen

                    if (getRowScore(state.field, Player.MIN, x, y, 1, 0)==4 || getRowScore(state.field, Player.MAX, x, y, 1, 0)== -4)
                        return true;  // verloren
                }
                if ((columns-x>=4) && (y>=3)) {
                    if (getRowScore(state.field, Player.MAX, x, y, 1, -1)==4 || getRowScore(state.field, Player.MIN, x, y, 1, -1)== -4)
                        return true;  // gewonnen

                    if (getRowScore(state.field, Player.MIN, x, y, 1, -1)==4 || getRowScore(state.field, Player.MAX, x, y, 1, -1)== -4)
                        return true;  // verloren
                }
            }
        }
		return false;		
	}
}