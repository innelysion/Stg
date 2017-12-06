package inmr.game;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GameManager {

	private int scene = 0;



	private GameData gd = new GameData(); // 资源管理器
	private GameGraphics gg = new GameGraphics(); // 图像管理器
	private StgPlayer player = new StgPlayer();

	GameManager() {
		gg.setGameData(gd);
	}

	public void updateLogic(){
		player.update();
	}

	public void updateGraphics(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer, JFrame window){
		gg.update(backLayer, midLayer, frontLayer, uiLayer, window);
	}

}

class GameSetting {
	final static int WINDOW_W = 1280;
	final static int WINDOW_H = 720;
	final static double FRAME_TIME = 0.017;
	final static int ENEMY_MAX = 500;
	final static int DANMAKU_MAX = 10000;
	final static int PLAYERSHOOT_MAX = 5000;
}

