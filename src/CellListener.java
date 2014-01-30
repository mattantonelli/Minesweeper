import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;


public class CellListener extends MouseAdapter {
	
	public enum ClickType {
		Left, Middle, Right
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Cell cell = (Cell) e.getSource();
		
		if(e.getClickCount() == 2) {
			cell.sweep(ClickType.Middle);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Cell cell = (Cell) e.getSource();
		cell.doPress();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Cell cell = (Cell) e.getSource();
		cell.doRelease();
		
		if(SwingUtilities.isLeftMouseButton(e)) {
			cell.sweep(ClickType.Left);
		} else if(SwingUtilities.isMiddleMouseButton(e)) {
			cell.sweep(ClickType.Middle);
		} else if(SwingUtilities.isRightMouseButton(e)) {
			cell.sweep(ClickType.Right);
		}
		
		if(cell.isClickable()) {
			cell.setMouseUp(false);
			cell.setMouseHover();
		} else {
			cell.setMouseUp(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Cell cell = (Cell) e.getSource();
		if(cell.isClickable()) {
			cell.setMouseHover();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cell cell = (Cell) e.getSource();
		cell.setMouseUp(true);
	}

}
