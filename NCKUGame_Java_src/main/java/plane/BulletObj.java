package plane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class BulletObj extends GameObj {

	public BulletObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BulletObj(Image img, int x, int y, int width, int height, double speed, GamePlane frame) {
		super(img, x, y, width, height, speed, frame);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		super.paintSelf(gImage);
		y += speed;
		
		if (!this.frame.planeObj.invulnerable && this.getRec().intersects(this.frame.planeObj.getRec())) {
			GamePlane.playSoundEffect("sound/hit.wav");
			this.frame.planeObj.invulnerable = true;
		}
		
		if (y > GamePlane.height) {
			this.x = -300;
			this.y = 300;
			GameImage.removeList.add(this);
		}
	}

	@Override
	public Rectangle2D.Double getRec() {
		// TODO Auto-generated method stub
		return super.getRec();
	}

	
}
