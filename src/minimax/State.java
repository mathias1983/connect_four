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
                	s += "X";
                else if (field[i][j] == -1)
                    s += "O";
                else
                    s += " ";
        	}
                s += "|\n";
        }
        return s;
    }
}
