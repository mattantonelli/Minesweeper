import java.util.Arrays;
import java.util.Random;


public class MineGenerator {

	private Random rand = new Random();
	
	public MineGenerator() {
		
	}
	
	public boolean[][] getMinefield(int width, int height, int numMines) {
		
		boolean[][] minefield = new boolean[width][height];
		for(int i = 0; i < minefield.length; i++) {
			Arrays.fill(minefield[i], false);
		}
		
		for(int i = 0; i < numMines; i++) {
			while(true) {
				int index = rand.nextInt(width * height);
				int col = index % width;
				int row = index / width;
				boolean isMine = minefield[col][row];
				if(!isMine) {
					minefield[col][row] = true;
					break;
				}
			}
		}
		
		return minefield;
	}
	
}
