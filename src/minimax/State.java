package minimax;

import config.GuiConfig;

public class State
{
	public int[][] field;
	
	public State()
	{
		field = new int[GuiConfig.BOARD_ROWS][GuiConfig.BOARD_COLUMNS];
		for(int i=0; i<GuiConfig.BOARD_ROWS; i++)
			for(int j=0; j<GuiConfig.BOARD_COLUMNS; j++)
				field[i][j] = 0;
	}

    /***
     * Fügt Spielstein nach den 4 gewinnt Regeln ein, damit keiner
     * "in der Luft" hängt.
     * @return
     */
    public int rule_insert( int x )
    {
        int start = (GuiConfig.BOARD_ROWS - 1);
        for(int y= start; y>=0; --y)
        {
            if( field[y][x] == 0 )
            {
                field[y][x] = 1;
                return y;
            }

        }

        return -1;
    }
    
    public int getHorizontalScore(int y, int startX, int endXInclusive)
    {
    	int score = 0;
    	
    	for(int x=startX; x<=endXInclusive && x<this.field[0].length; x++)
    		score += this.field[y][x]; 
    	return score;
    }
    
    public int getVerticalScore(int x, int startY, int endYInclusive)
    {
    	int score = 0;
    	
    	for(int y=startY; y<=endYInclusive && y<this.field.length; y++)
    		score += this.field[y][x]; 
    	return score;
    }
    
    public int getDiagonalScore(int startX, int startY, int length, int yDelta)
    {
    	int score = 0, y = startY;
    	
    	for(int x=startX; x<startX+length && x<this.field[0].length; x++)
//    		for(int y=startY; y<startY+length && y<this.field.length; y+=yDelta)
    	{
    		score += this.field[y][x];
    		y+=yDelta;
    		
    		if(y>=this.field.length || y<0)
    			break;
    	}
    	
    	return score;
    }

	public State deepCopy()
	{
		State copy = new State();
		
		for(int i=0; i<GuiConfig.BOARD_ROWS; i++)
			for(int j=0; j<GuiConfig.BOARD_COLUMNS; j++)
				copy.field[i][j] = this.field[i][j];
		
		return copy;
	}
	
    public String toString() 
    {
    	String s = "";
        for (int i=0; i<GuiConfig.BOARD_ROWS; i++)
        {
        	s += "|";
        	for (int j=0; j<GuiConfig.BOARD_COLUMNS; j++)
        	{
                if (field[i][j] == 1)
                	s += "0|";
                else if (field[i][j] == -1)
                    s += "x|";
                else
                    s += " |";
        	}
                s += "\n";
        }
        return s;
    }
}
