package plane;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameObj {
	
	Image img;
	int x, y, width, height;
	double speed;
	GamePlane frame;
	
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
	public Window getFrame() {
		return frame;
	}
	public void setFrame(GamePlane frame) {
		this.frame = frame;
	}
	
	public GameObj() {
	}
	
	public GameObj(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public GameObj(Image img, int x, int y, double speed) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public GameObj(Image img, int x, int y, int width, int height, double speed, GamePlane frame) {
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
	}
	
	public Rectangle2D.Double getRec() {
		return new Rectangle2D.Double(x,y,width,height);
	}
}
