package minimax;

import config.GuiConfig;

import java.util.ArrayList;
import java.util.List;

public class Minimax 
{	
	public enum Player 
	{MIN, MAX};

    private static final int POS_INFINITY = (int)Double.POSITIVE_INFINITY;
    private static final int NEG_INFINITY = (int)Double.NEGATIVE_INFINITY;
	
	Player player = Player.MAX;
	public static int boardSize;

	public Minimax(Player p)
	{
		this.player = p;

	}
	
	public State getMinimaxDecision(State initialState) 
	{
		System.out.println("player: " + player);
		long t1 = System.currentTimeMillis();
		
		Action bestAction = null;
		double bestUtility = Double.NEGATIVE_INFINITY;
		List<Action> actionList = getActions(initialState);
		
		for (Action action : actionList) 
		{
			double utility = minValue(getResult(initialState, action));
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
	
	private double minValue(State state) 
	{
		double utility = Double.POSITIVE_INFINITY;
		
		if (terminalTest(state))
		{
//			System.out.println("terminal: ");
//			System.out.println(state.toString());
//			System.out.println("");
			return utility(state);
		}
		
		List<Action> actionList = getActions(state);
		
		for (Action action: actionList) 
		{
			double tmp = maxValue(getResult(state, action));
			utility = Math.min(utility,tmp);
		}
		return utility;
	}
	
	private double maxValue(State state) 
	{
		double utility = Double.NEGATIVE_INFINITY;
		
		if (terminalTest(state))
		{
//			System.out.println("terminal: ");
//			System.out.println(state.toString());
//			System.out.println("");
			return utility(state);
		}

		List<Action> actionList = getActions(state);
		
		for (Action action: actionList) 
		{
			double tmp = minValue(getResult(state,action));
			utility = Math.max(utility, tmp);
		}
		return utility;
	}

	private State getResult(State currentState, Action action) 
	{
		State resultState = currentState.deepCopy(); 

		if (action.player == Player.MAX) 
			resultState.field[action.col][action.row] = 1;
		else 
			resultState.field[action.col][action.row] = -1;
		
//		System.out.println("CURRENT RESULT: ");
//		System.out.println(resultState.toString());
//		System.out.println("");
	
		return resultState;
	}
	
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
            // ganz unten im spielbrett(höchster y wert) zuerst nach leerem Feld suchen
            // wenn keines gefunden ein feld darüber suchen
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
                // ein feld höher gehen
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
        if (  ((field[x][y]==player) || (field[x][y]==0))
                      && ((field[x+1*dx][y+1*dy]==player) || (field[x+1*dx][y+1*dy]==0))
                      && ((field[x+2*dx][y+2*dy]==player) || (field[x+2*dx][y+2*dy]==0))
                      && ((field[x+3*dx][y+3*dy]==player) || (field[x+3*dx][y+3*dy]==0))) {

            // zaehle Anzahl von spieler belegter Felder
            for (int i=0; i<4; i++) if (field[x+i*dx][y+i*dy]==player) num++;
        }
        return num;
    }

    private int utility( State state )
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
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips?
                    else if (is_row(field, Player.MAX, x, y, 0, 1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==3)
                        min3++;
                        // 2 gleiche Chips?
                    else if (is_row(field, Player.MAX, x, y, 0, 1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 0, 1)==2)
                        min2++;
                }
                // Noch 4 Chips nach rechts oben moeglich?
                if ((rows-y>=4) && (columns-x>=4)) {
                    // 4 gleiche Chips nach rechts oben?
                    if (is_row(field, Player.MAX, x, y, 1, 1)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==3)
                        min3++;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, 1)==2)
                        min2++;
                }
                // Noch 4 Chips nach rechts moeglich?
                if (columns-x>=4) {
                    if (is_row(field, Player.MAX, x, y, 1, 0)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 0)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==3)
                        min3++;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, 0)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, 0)==2)
                        min2++;
                }
                // Noch 4 Chips nach rechts unten moeglich?
                if ((columns-x>=4) && (y>=3)) {
                    if (is_row(field, Player.MAX, x, y, 1, -1)==4)
                        return POS_INFINITY;  // gewonnen
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==4)
                        return NEG_INFINITY;  // verloren
                        // 3 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, -1)==3)
                        max3++;
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==3)
                        min3++;
                        // 2 gleiche Chips uebereinander?
                    else if (is_row(field, Player.MAX, x, y, 1, -1)==2)
                        max2++;
                    else if (is_row(field, Player.MIN, x, y, 1, -1)==2)
                        min2++;
                }
            }
        }

       return 10*max2 + 20*max3 - 10*min2 - 20*min3;
    }



    /*
	private int utility(State state) 
	{
		int val = 0;
		for (int i=0; i<boardSize; i++) 
			val += state.field[i][i];
	
		if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN)
            return 1;
    
		if ( val == boardSize && player == Player.MIN || val == -boardSize && player == Player.MAX) 
            return -1;
		
		val=0;
		for (int i=0; i<boardSize; i++) 
			val += state.field[i][boardSize-i-1];

		if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
            return 1;
    
		if ( val == boardSize && player == Player.MIN || val == -boardSize && player == Player.MAX) 
            return -1;

		for (int i=0; i<boardSize; i++) 
		{
			val = 0;
			for (int j=0; j<boardSize; j++) 
				val += state.field[i][j];

			if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
	            return 1;
	    
			if ( val == boardSize && player == Player.MIN || val == -boardSize && player == Player.MAX) 
	            return -1;
		}

		for (int j=0; j<boardSize; j++) 
		{
			val = 0;
			for (int i=0; i<boardSize; i++) 
				val += state.field[i][j];

			if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
	            return 1;
	    
			if ( val == boardSize && player == Player.MIN || val == -boardSize && player == Player.MAX) 
	            return -1;
		}
		return 0;
	}
	*/
	
	public boolean terminalTest(State state) 
	{
		int playedFields = 0;
        int columns = GuiConfig.BOARD_COLUMNS;
        int rows    = GuiConfig.BOARD_ROWS;
		
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
		
		if (playedFields == rows*columns)
			return true;

		for (int j=0; j<rows; j++)
		{
			int total = 0;
			for (int i=0; i<columns; i++)
				total += state.field[i][j];

			if (Math.abs(total) == columns)
				return true;
		}

        // diagonal
		int total = 0;
		for (int i=0; i<boardSize; i++) 
			total += state.field[i][i];
		
		if (Math.abs(total) == boardSize) 
			return true;
		
		total=0;
		for (int i=0; i<boardSize; i++) 
			total += state.field[i][boardSize-i-1];
		
		if (Math.abs(total) == boardSize) 
			return true;
		
		return false;		
	}
}