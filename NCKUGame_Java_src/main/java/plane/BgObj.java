package plane;

import java.awt.Graphics;
import java.awt.Image;

public class BgObj extends GameObj{

	public BgObj() {
	}
	
	public BgObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}
	
	@Override
	public void paintSelf(Graphics gImage) {
		super.paintSelf(gImage);
		y += speed;
		if (y >= 0) {
			y = -4800;
		}
	}
	
}
