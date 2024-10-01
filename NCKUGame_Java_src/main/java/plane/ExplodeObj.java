package plane;

import java.awt.*;

public class ExplodeObj extends GameObj {

	public static Image[] pic = new Image[9];
	
	int explodeCount = 0;
	
	static {
		for (int i = 0; i < pic.length; i++) {
			pic[i] = Toolkit.getDefaultToolkit().getImage("planeImgs/expl"+i+".png");
		}
	}
	@Override
	public void paintSelf(Graphics gImage) {
		// TODO Auto-generated method stub
		if (explodeCount < 9) {
			super.img =  pic[explodeCount];
			super.paintSelf(gImage);
			explodeCount++;
		}
	}

	public ExplodeObj(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	
}
