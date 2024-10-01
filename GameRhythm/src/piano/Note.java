package piano;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

public class Note extends Obj{
	
	Image img;
	int x;
	int y;
	int width;
	int height;
	double speed;
	Piano frame;
	
	public static Image noteImage = Toolkit.getDefaultToolkit().getImage("imgs/note.png");
	public static List<Note> noteList = new ArrayList<>();
	
	
	public Image getImg() {
		return img;
	}


	public void setImg(Image img) {
		this.img = img;
	}


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


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public Piano getFrame() {
		return frame;
	}


	public void setFrame(Piano frame) {
		this.frame = frame;
	}


	public Note() {
	}

	public Note(Image img, int x, int y, int width, int height, double speed, Piano frame) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.frame = frame;
	}
	
	public void paintSelf(Graphics gImage) {
		gImage.drawImage(img, x, y, null);
		y += speed;
	}
	
	public Rectangle getRec() {
		return new Rectangle(x,y,width,height);
	}
	
}
