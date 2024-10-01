package piano;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Btn extends Obj{

	Image img;
	int x;
	int y;
	int width;
	int height;
	String str;
	Piano frame;
	int delete = 0;
	
	public static Image btnImage = Toolkit.getDefaultToolkit().getImage("imgs/button.png");
	public static Image btn2Image = Toolkit.getDefaultToolkit().getImage("imgs/button2.png");
	public static List<Btn> btnList = new ArrayList<>();
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	
	public Btn(Image img, int x, int y, int width, int height, String str, Piano frame) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.str = str;
		this.frame = frame;
	}
	
	public void paintSelf(Graphics gImage) {
		gImage.drawImage(img, x, y, null);
		gImage.drawString(str, x+width/2, y+height/2);
	}
	
}
