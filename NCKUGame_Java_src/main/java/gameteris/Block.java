package gameteris;


import java.awt.Color;
import java.util.Random;

public class Block {

	static int [] allRect;
	int rect;
	int x, y;
	
	public void initBlock() {
		allRect = new int[] {0x8888, 0xe400, 0xe800, 0xe200, 0x6c00, 0xc600, 0xcc00,
							 0x000f, 0x8c80, 0x4e00, 0x4c40, 0x88c0, 0x2e00, 0xc440,
							 0xc880, 0x8e00, 0x44c0, 0x8c40, 0x4c80};
	}

	public void ranRect() {
		Random random = new Random();
		rect = allRect[random.nextInt(7)];
		x = 0;
		y = 5;
	}
	
	public boolean canFall(int m, int n) {
		
		int temp = 0x8000;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((temp & rect) != 0) {
					if (Tetris.data[m+1][n] == 1) {
						return false;
					}
				}
				n++;
				temp >>= 1;
			}
			m++;
			n = n-4;
		}
		return true;
	}
	
	public boolean canTurn(int a, int m, int n) {
		
		int temp = 0x8000;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((a & temp) != 0) {
					if (Tetris.data[m][n] == 1) {
						return false;
					}
				}
				n++;
				temp >>= 1;
			}
			m++;
			n = n-4;
		}
		return true;
	}
	
	public void changeData(int m, int n) {
		
		int temp = 0x8000;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((temp & rect) != 0) {
					Tetris.data[m][n] = 1;
				}
				n++;
				temp >>= 1;
			}
			m++;
			n = n-4;
		}
	}
	
	public void removeRow(int row) {
		
		int temp = 100;
		for (int i = row; i >=1; i--) {
			for (int j = 1; j < Tetris.game_column-1; j++) {
				Tetris.data[i][j] = Tetris.data[i-1][j];
			}
		}
		
		refresh(row);
		
//		if (Tetris.time > temp) {
//			Tetris.time -= temp;
//		}
		
		Tetris.score += temp;
		Tetris.label_score.setText("Score: " + Tetris.score);
	}
	
	public void refresh(int row) {
		
		for (int i = row; i >=1; i--) {
			for (int j = 1; j < Tetris.game_column-1; j++) {
				if (Tetris.data[i][j] == 1) {
					Tetris.text[i][j].setBackground(Color.BLUE);
				} else {
					Tetris.text[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	public void fall(int m, int n) {
		if (m > 0) {
			clear(m-1, n);
		}
		
		draw(m, n);
	}
	
	public void clear(int m, int n) {
		
		int temp = 0x8000;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((temp & rect) != 0) {
					Tetris.text[m][n].setBackground(Color.WHITE);
				}
				n++;
				temp >>= 1;
			}
			m++;
			n = n-4;
		}
	}
	
	public void draw(int m, int n) {
		int temp = 0x8000;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((temp & rect) != 0) {
					Tetris.text[m][n].setBackground(Color.BLUE);
				}
				n++;
				temp >>= 1;
			}
			m++;
			n = n-4;
		}
	}
	
}