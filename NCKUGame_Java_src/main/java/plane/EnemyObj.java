package plane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class EnemyObj extends GameObj {

	
	public EnemyObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnemyObj(Image img, int x, int y, int width, int height, double speed, GamePlane frame) {
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
			this.x = -200;
			this.y = 200;
			GameImage.removeList.add(this);
		}
		
		for (ShellObj shellObj: GameImage.shellObjList) {
			if (this.getRec().intersects(shellObj.getRec())) {
				ExplodeObj explodeObj = new ExplodeObj(x,y);
				GameImage.explodeObjList.add(explodeObj);
				GameImage.removeList.add(explodeObj);
				GamePlane.playSoundEffect("sound/expl1.wav");
				shellObj.setX(-100);
				shellObj.setY(100);
				this.setX(-200);
				this.setY(200);
				GameImage.removeList.add(shellObj);
				GameImage.removeList.add(this);
				if (GamePlane.score < 40) GamePlane.score++;
			}
		}
	}

	@Override
	public Rectangle2D.Double getRec() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(x+10,y,width,height);
	}

}
