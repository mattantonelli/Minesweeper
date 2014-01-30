import java.util.LinkedList;



public class Minefield {

	private static MineGenerator generator = new MineGenerator();
	
	private final MinesolverPanel parent;
	private Cell[][] minefield;
	
	private boolean gameover = false;
	
	private final int width, height;
	private int numFlags = 0;
	
	public Minefield(MinesolverPanel parent, int width, int height, int numMines) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		
		minefield = new Cell[width][height];
		
		boolean[][] basicMinefield = generator.getMinefield(width, height, numMines);
		
		for(int col = 0; col < basicMinefield.length; col++) {
			for(int row = 0; row < basicMinefield[0].length; row++) {
				Cell cell = new Cell(this, col, row, basicMinefield[col][row]);
				minefield[col][row] = cell;
				parent.add(minefield[col][row]);
				cell.validate();
			}
		}
		
		for(int col = 0; col < minefield.length; col++) {
			for(int row = 0; row < minefield[0].length; row++) {
				minefield[col][row].setNumAdjacent(getNumAdjacentMines(col, row));
			}
		}
	}

	/**
	 * @return the number of mines adjacent to the specified cell
	 */
	public int getNumAdjacentMines(int x, int y) {
		int numAdjacent = 0;
		
		LinkedList<Cell> adjacent = getAdjacentCells(x, y);
		for(Cell cell : adjacent) {
			if(cell.isMine()) numAdjacent++;
		}
		
		return numAdjacent;
	}
	
	/**
	 * @return the number of flagged mines adjacent to the specified cell
	 */
	public int getNumFlaggedAdjacentMines(int x, int y) {
		int numFlagged = 0;
		
		LinkedList<Cell> adjacent = getAdjacentCells(x, y);
		for(Cell cell : adjacent) {
			if(cell.isFlagged()) numFlagged++;
		}
		
		return numFlagged;
	}
	
	/**
	 * Sweeps all remaining mystery cells adjacent to the specfied cell if the
	 * number of adjacent flags is equal to the number of adjacent mines indicated
	 * by the cell
	 */
	public void sweepAdjacent(int x, int y) {
		if(minefield[x][y].getNumAdjacent() == getNumFlaggedAdjacentMines(x, y)) {
			LinkedList<Cell> adjacent = getAdjacentCells(x, y);
			for(Cell cell : adjacent) {
				if(cell.isMystery()) {
					cell.sweep();
				}
			}
		}
	}
	
	/**
	 * @return a list of cells adjacent to the specified cell
	 */
	private LinkedList<Cell> getAdjacentCells(int x, int y) {
		LinkedList<Cell> adjacent = new LinkedList<Cell>();
		
		for(int col = x - 1; col < x + 2; col++) {
			if(col > -1 && col < width) {
				for(int row = y - 1; row < y + 2; row++) {
					if(row > -1 && row < height) {
						adjacent.push(minefield[col][row]);
					}
				}
			}
		}
		
		return adjacent;
	}
	
	/**
	 * Removes all cells from the parent when the minefield is reset
	 */
	public void clear() {
		for(Cell[] row : minefield) {
			for(Cell cell : row) {
				parent.remove(cell);
			}
		}
	}

	/**
	 * Checks to see if all non-mine cells have been sweeped. Returns
	 * if an unsweeped cell has been found, otherwise sets victory once
	 * all cells have been checked.
	 */
	private void checkVictory() {
		for(Cell[] row : minefield) {
			for(Cell cell : row) {
				if((cell.isMystery() && !cell.isMine()) || 
						(cell.isMine() && !cell.isFlagged())) {
					return;
				}
			}
		}
		
		parent.setVictory();
	}

	/**
	 * Reveals all cells on the field and signals the parent that the
	 * game has ended.
	 */
	public void setGameover() {
		for(Cell[] row : minefield) {
			for(Cell cell : row) {
				cell.reveal();
			}
		}
		
		gameover = true;
		parent.setGameover();
	}
	
	public int getNumFlags() {
		return numFlags;
	}
	
	public void addFlag() {
		numFlags++;
	}
	
	public void removeFlag() {
		numFlags--;
	}
	
	public void setMouseDown() {
		parent.setMouseDown();
	}
	
	public void setMouseHover() {
		parent.setMouseHover();
	}
	
	public void setMouseUp(boolean changeCursor) {
		parent.setMouseUp(changeCursor);
		if(!gameover) checkVictory();
	}
	
}
