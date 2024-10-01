package plane;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GameImage {
	public static Image bgImg = Toolkit.getDefaultToolkit().getImage("planeImgs/bg.jpg");
	public static Image bossImg = Toolkit.getDefaultToolkit().getImage("planeImgs/boss.png");
	public static Image explodeImg = Toolkit.getDefaultToolkit().getImage("planeImgs/explode.png");
	public static Image playerImg = Toolkit.getDefaultToolkit().getImage("planeImgs/player.png");
	public static Image shellImg = Toolkit.getDefaultToolkit().getImage("planeImgs/shell.png");
	public static Image bulletImg = Toolkit.getDefaultToolkit().getImage("planeImgs/bullet.png");
	public static Image loseImg = Toolkit.getDefaultToolkit().getImage("planeImgs/lose.png");
	public static Image foodImg = Toolkit.getDefaultToolkit().getImage("planeImgs/food.png");
	public static Image stoneImg = Toolkit.getDefaultToolkit().getImage("planeImgs/stone.png");
	public static Image[] enemyImg = new Image[7];
	static {
		for (int i = 0; i < enemyImg.length; i++) {
			enemyImg[i] = Toolkit.getDefaultToolkit().getImage("planeImgs/enemy"+i+".png");
		}
	}
	
	public static List<GameObj> gameObjList = new ArrayList<>();
	public static List<GameObj> removeList = new ArrayList<>();
	public static List<ShellObj> shellObjList = new ArrayList<>();
	public static List<BulletObj> bulletObjList = new ArrayList<>();
	public static List<EnemyObj> enemyObjList = new ArrayList<>();
	public static List<ExplodeObj> explodeObjList = new ArrayList<>();
	
	public static void drawWord(Graphics gImage, String str, Color color, int size, int x, int y) {
		gImage.setColor(color);
		gImage.setFont(new Font("Arial", Font.BOLD, size));
		gImage.drawString(str, x, y);
	}
}
