package rr.rr1223;
import java.awt.*;



public class Eater {
	Image img = GameImage.Me;
	Image img2 = GameImage.MeSuper;
	//position
	int x = 700;
	int y = 500;
	int width = 80;
	int height = 80;
	
	int speed = 30;
	
	int level = 1;
	
	
	void logic() {
		if(GameImage.UP) {
			y = y - speed;
			if(y <= 3) y = 790;
		}
		if(GameImage.DOWN) {
			y = y + speed;
			if(y >= 790) y = 3;
		}
		if(GameImage.LEFT) {
			x = x - speed;
			if(x <= 3) x = 1100;
		}
		if(GameImage.RIGHT) {
			x = x + speed;
			if(x >= 1100) x = 3;

		}
		
	}
	
	
	public void paintSelf(Graphics g) {
		logic();
		if(GameFoodie.state == 3) {
			speed = 40;
			g.drawImage(img2, x, y, width+120, height+120, null);
		}else {
			g.drawImage(img, x, y, width+GameImage.score, height+GameImage.score, null);
	
		}
	}
	
	public Rectangle getRec() {
		if(GameFoodie.state == 3) {
			return new Rectangle(x, y, width+120, height+120);
		}else {
			return new Rectangle(x, y, width+GameImage.score, height+GameImage.score);
	
		}
	}
}
