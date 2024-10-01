package plane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class BossObj extends GameObj {
	
	public int life = 50;

	public BossObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BossObj(Image img, int x, int y, int width, int height, double speed, GamePlane frame) {
		super(img, x, y, width, height, speed, frame);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		if (x < 0 || x > GamePlane.width - this.width) {
			speed = -speed;
			x += speed;
		}
		x += speed;
		for (ShellObj shellObj: GameImage.shellObjList) {
			if (this.getRec().intersects(shellObj.getRec())) {
				shellObj.setX(-100);
				shellObj.setY(100);
				GameImage.removeList.add(shellObj);
				life--;
			}
			if (life <= 0) {
				GamePlane.playSoundEffect("sound/expl0.wav");
				GamePlane.state = 4;
			}
		}

		gImage.setColor(Color.WHITE);
		gImage.fillRect(20, 40, 50, 10);
		
		gImage.setColor(Color.RED);
		gImage.fillRect(20, 40, life, 10);
	}

	
	@Override
	public Rectangle2D.Double getRec() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(x+20,y,width,height);
	}

}
