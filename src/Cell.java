import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Cell extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private final Minefield minefield;
	private static CellListener cellListener = new CellListener();
	private CellType type;
	
	private final Dimension SIZE = new Dimension(32, 32);
	
	private static ImageIcon cellIcon = new ImageIcon("img/cell.png"),
			cellClickIcon = new ImageIcon("img/cell_click.png"),
			cell1Icon = new ImageIcon("img/cell_1.png"), cell2Icon = new ImageIcon("img/cell_2.png"),
			cell3Icon = new ImageIcon("img/cell_3.png"), cell4Icon = new ImageIcon("img/cell_4.png"),
			cell5Icon = new ImageIcon("img/cell_5.png"), cell6Icon = new ImageIcon("img/cell_6.png"),
			cell7Icon = new ImageIcon("img/cell_7.png"), cell8Icon = new ImageIcon("img/cell_8.png"),
			cell0Icon = new ImageIcon("img/cell_0.png"), cellMineIcon = new ImageIcon("img/cell_mine.png"),
			cellBoomIcon = new ImageIcon("img/cell_boom.png"), cellFlagIcon = new ImageIcon("img/cell_flag.png"),
			cellFlagClickIcon = new ImageIcon("img/cell_flag_click.png"),
			cellBadFlagIcon = new ImageIcon("img/cell_badflag.png");
	
	private final boolean isMine;
	
	private final static int width = 32, height = width;
	private final int x, y;
	private int numAdjacent = -1;
	
	private enum CellType {
		Mystery, Mine, Flag, Blank
	}
	
	public Cell(Minefield minefield, int x, int y, boolean isMine) {
		this.minefield = minefield;
		this.x = x;
		this.y = y;
		this.isMine = isMine;
		this.type = CellType.Mystery;
		
		setIcon(cellIcon);
		setSize(SIZE);
		setLocation(x * width, y * height + 32);
		addMouseListener(cellListener);
	}
	
	/**
	 * Sweeps a cell, either exposing a blank, exploding a mine, or setting a flag
	 * @param clickType the type of click that triggered the sweep event
	 */
	public void sweep(CellListener.ClickType clickType) {		
		switch(clickType) {
		case Left:
			if(type != CellType.Mystery) break;
			
			if(isMine) {
				setBoom();
				minefield.setGameover();
			} else {
				setBlank();
			}
			break;
		case Middle:
			minefield.sweepAdjacent(x, y);
			break;			
		case Right:
			setFlag();
			break;
		default:
			break;
		}	
	}
	
	/**
	 * Default left click sweep method
	 */
	public void sweep() {
		sweep(CellListener.ClickType.Left);
	}
	
	/**
	 * Toggles a flag on a mystery cell
	 */
	private void setFlag() {
		if(type == CellType.Flag) {
			setIcon(cellIcon);
			type = CellType.Mystery;
			minefield.removeFlag();
		} else if(type == CellType.Mystery) {
			setIcon(cellFlagIcon);
			type = CellType.Flag;
			minefield.addFlag();
		}
	}
	
	/**
	 * Does calculation for newly sweeped blank mystery cell
	 */
	private void setBlank() {
		setBlankIcon(numAdjacent);
		type = CellType.Blank;
		if(numAdjacent == 0) minefield.sweepAdjacent(x, y);
	}
	
	/**
	 * Sets blank cell icon with a number based on the number of adjacent mines
	 */
	private void setBlankIcon(int numAdjacent) {
		switch(numAdjacent) {
		case 0:
			setIcon(cell0Icon);
			break;
		case 1:
			setIcon(cell1Icon);
			break;
		case 2:
			setIcon(cell2Icon);
			break;
		case 3:
			setIcon(cell3Icon);
			break;
		case 4:
			setIcon(cell4Icon);
			break;
		case 5:
			setIcon(cell5Icon);
			break;
		case 6:
			setIcon(cell6Icon);
			break;
		case 7:
			setIcon(cell7Icon);
			break;
		case 8:
			setIcon(cell8Icon);
			break;
		default: break;
		}
	}
	
	/**
	 * Sets a cell icon as a mine
	 */
	private void setMine() {
		setIcon(cellMineIcon);
		type = CellType.Mine;
	}
	
	/**
	 * Sets a cell has an exploded mine
	 */
	private void setBoom() {
		setIcon(cellBoomIcon);
		type = CellType.Mine;
	}
	
	/**
	 * Sets cell with pressed icon if applicable
	 */
	public void doPress() {
		if(type == CellType.Mystery) {
			setIcon(cellClickIcon);
		} else if(type == CellType.Flag) {
			setIcon(cellFlagClickIcon);
		}
		
		if(isClickable()) minefield.setMouseDown();
	}
	
	
	/**
	 * Resets cell to unpressed icon if applicable
	 */
	public void doRelease() {
		if(type == CellType.Mystery) {
			setIcon(cellIcon);
		} else if(type == CellType.Flag) {
			setIcon(cellFlagIcon);
		}
	}
	
	/**
	 * Reveals if the cell is a mine or a blank, or marks its current
	 * flag as incorrect if the cell was incorrectly flagged as a mine
	 */
	void reveal() {
		if(isMine && type != CellType.Mine && type != CellType.Flag) {
			setMine();
		} else if(type == CellType.Mystery) {
			setBlank();
		} else if(type == CellType.Flag && !isMine) {
			setIcon(cellBadFlagIcon);
		}
	}
	
	public int getNumAdjacent() {
		return numAdjacent;
	}
	
	public void setNumAdjacent(int numAdjacent) {
		this.numAdjacent = numAdjacent;
	}
	
	public boolean isClickable() {
		return type == CellType.Mystery || type == CellType.Flag;
	}
	
	public boolean isFlagged() {
		return type == CellType.Flag;
	}
	
	public boolean isMine() {
		return isMine;
	}
	
	public boolean isMystery() {
		return type == CellType.Mystery;
	}
	
	public void setMouseHover() {
		minefield.setMouseHover();
	}
	
	public void setMouseUp(boolean changeCursor) {
		minefield.setMouseUp(changeCursor);
	}

}
