package inmr.game;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GameManager {

	private int scene = 0;
	private int timerMain = 0;

	private GameData gd = new GameData(); // 资源管理器
	private GameGraphics gg = new GameGraphics(); // 图像管理器
	private StgPlayer player = new StgPlayer();
	private StgEnemy enemy = new StgEnemy();

	GameManager() {
		gg.setGameData(gd);
		// TEST
		gg.drawlist.add(player);
		gg.drawlist.add(enemy);
	}

	public void updateLogic() {
		switch (scene) {
		case 0:
			player.update();
			enemy.update();
			break;
		case 1:
			break;
		}
	}

	public void updateGraphics(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer,
			JFrame window) {
		gg.update(scene, backLayer, midLayer, frontLayer, uiLayer, window);
		if (player.hit(0, enemy, 0)) {
			uiLayer.drawString("HIT!!", 100, 100);
		}
		uiLayer.setColor(Color.white);
	}

}

class GameSetting {

	final static int WINDOW_W = 1280;
	final static int WINDOW_H = 720;
	final static int ENEMY_MAX = 500;
	final static int DANMAKU_MAX = 10000;
	final static int PLAYERSHOOT_MAX = 5000;

	final static int[][] STAGE_DATA = { //
			{ 1000, 2000, 3000, 4000 }, // timeline
			{ 1, 2, 3, 4 } // request enemy
	};

}
