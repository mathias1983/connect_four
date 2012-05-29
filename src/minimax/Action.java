package minimax;

import minimax.Minimax.Player;

public class Action 
{
	public int row;
    public int col;
    public Player player;
    
	public Action deepCopy()
	{
		Action copy = new Action();
		
		copy.row = this.row;
		copy.col = this.col;
		copy.player = this.player;
		
		return copy;
	}
}
