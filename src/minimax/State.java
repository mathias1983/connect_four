package minimax;

public class State 
{
	public int[][] field;
	
	public State()
	{
		field = new int[Minimax.boardSize][Minimax.boardSize];
		for(int i=0; i<Minimax.boardSize; i++)
			for(int j=0; j<Minimax.boardSize; j++)
				field[i][j] = 0;
	}

	public State deepCopy()
	{
		State copy = new State();
		
		for(int i=0; i<Minimax.boardSize; i++)
			for(int j=0; j<Minimax.boardSize; j++)
				copy.field[i][j] = this.field[i][j];
		
		return copy;
	}
	
    public String toString() 
    {
    	String s = "";
        for (int i=0; i<Minimax.boardSize; i++) 
        {
        	s += "|";
        	for (int j=0; j<Minimax.boardSize; j++) 
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
