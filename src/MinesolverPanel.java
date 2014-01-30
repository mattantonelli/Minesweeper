import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MinesolverPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel timeLbl = new JLabel(), smileLbl = new JLabel(),
			numMinesLbl = new JLabel();
	
	private final ImageIcon smileIcon = new ImageIcon("img/smile.png"),
			smileScaredIcon = new ImageIcon("img/smile_scared.png"), 
			smileLoseIcon = new ImageIcon("img/smile_lose.png"), 
			smileWinIcon = new ImageIcon("img/smile_win.png");
	
	private Timer timer;
	
	private final int width = 16, height = 16, numMines = 40;
	private int elapsedTime = 0;
	
	private boolean gameover = false, victory = false;
	
	private Minefield minefield;
	
	public MinesolverPanel() {		
		timer = new Timer(1000, new TimerListener());
		
		setLayout(null);
		initializeGame();
		
		timeLbl.setLocation(8, 0);
		timeLbl.setSize(100, 32);
		add(timeLbl);
		
		smileLbl.setLocation(240, 0);
		smileLbl.setSize(32, 32);
		smileLbl.setIcon(smileIcon);
		smileLbl.addMouseListener(new SmileListener());
		add(smileLbl);
		
		numMinesLbl.setLocation(450, 0);
		numMinesLbl.setSize(100, 32);
		
		add(numMinesLbl);
	}
	
	private void initializeGame() {
		if(minefield != null) minefield.clear();
		minefield = new Minefield(this, width, height, numMines);
		setSmile();
		
		timeLbl.setText("Time: 0:00");
		numMinesLbl.setText("Mines: " + numMines);
		elapsedTime = 0;
		
		timer.start();
		repaint();
	}
	
	public void setVictory() {
		victory = true;
		timer.stop();
		setSmileWin();
	}
	
	public void setGameover() {
		gameover = true;
		timer.stop();
		setSmileLose();
	}
	
	private void setSmile() {
		if(!gameover && !victory) smileLbl.setIcon(smileIcon);
	}
	
	private void setSmileScared() {
		smileLbl.setIcon(smileScaredIcon);
	}
	
	private void setSmileLose() {
		smileLbl.setIcon(smileLoseIcon);
	}
	
	private void setSmileWin() {
		smileLbl.setIcon(smileWinIcon);
	}
	
	public void setMouseDown() {
		setSmileScared();
	}
	
	public void setMouseHover() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void setMouseUp(boolean changeCursor) {
		if(changeCursor) setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		numMinesLbl.setText("Mines: " + Math.max(0, numMines - minefield.getNumFlags()));
		setSmile();
	}
	
	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			elapsedTime++;
			
			String mins = "" + elapsedTime / 60;
			String secs = "" + elapsedTime % 60;
			
			if(secs.length() == 1) {
				secs = "0" + secs;
			}
			
			timeLbl.setText("Time: " + mins + ":" + secs);
		}

	}
	
	public class SmileListener extends MouseAdapter {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			setMouseHover();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setMouseUp(true);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			setMouseDown();
			setSmileScared();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			setSmile();			
			if(gameover || victory) {
				gameover = false;
				victory = false;
				initializeGame();
			}
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(518, 573);
		
		MinesolverPanel panel = new MinesolverPanel();
		frame.add(panel);
	
		frame.setTitle("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
}
