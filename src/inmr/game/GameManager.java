package inmr.game;

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
		if (hit(player, 0, enemy, 0)) {
			uiLayer.drawString("HIT!!", 100, 100);
		}
	}

	boolean hit(StgObjects oa, int ia, StgObjects ob, int ib) {
		if (!oa.hitable[ia] || !ob.hitable[ib] || oa.size[ia][0] == 0 || ob.size[ib][0] == 0) {
			return false;
		}
		boolean hit = false;
		double xa = oa.dX[ia];
		double ya = oa.dY[ia];
		double wa = oa.size[ia][1];
		double ha = oa.size[ia][2];
		double xb = ob.dX[ib];
		double yb = ob.dY[ib];
		double wb = ob.size[ib][1];
		double hb = ob.size[ib][2];
		boolean a_is_round = oa.size[ia][0] == 2 ? true : false;
		boolean b_is_round = ob.size[ib][0] == 2 ? true : false;
		if (a_is_round && b_is_round) {
			double rX = Math.pow(((xa + wa) - (xb + wb)), 2);
			double rY = Math.pow(((ya + wa) - (yb + wb)), 2);
			hit = Math.sqrt(rX + rY) <= wa / 2 + wb / 2 ? true : false;
		} else if (a_is_round && !b_is_round) {

		} else if (!a_is_round && b_is_round) {

		} else {

		}
		return hit;
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
