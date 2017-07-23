package model.data.gameObjects;

import java.io.Serializable;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Position data members

	private int row = 0;
	private int col = 0;

	// c'tors
	public Position(int row, int col) // c'tor
	{
		this.row = row;
		this.col = col;
	}

	public Position(Position p) // copy c'tor
	{
		this.row = p.row;
		this.col = p.col;
	}

	public Position() // default c'tor
	{
		this.row = 0;
		this.col = 0;
	}

	// getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) throws Exception {
		if (row >= 0)
			this.row = row;
		else
			throw new Exception("Invalid position parameters.");
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) throws Exception {
		if (col >= 0)
			this.col = col;
		else
			throw new Exception("Invalid position parameters.");
	}
	
	@Override
	public String toString() {
		return "(" + row + "," + col +")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position){
			Position p = (Position) obj;
			if(this.row == p.row && this.col == p.col)
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
