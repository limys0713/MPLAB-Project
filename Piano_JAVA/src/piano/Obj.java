package piano;

import java.awt.Graphics;
import java.awt.Image;

public class Obj {

	Image img;
	int x;
	int y;
	int width;
	int height;
	Piano frame;
	

	public void paintSelf(Graphics gImage) {
		gImage.drawImage(img, x, y, null);
	}
}
