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
		if (hit(player, 0, enemy, 0)) {
			uiLayer.drawString("HIT!!", 100, 100);
		}
		uiLayer.setColor(Color.white);
	}

	private boolean hit(StgObjects oa, int ia, StgObjects ob, int ib) {
		// 不可碰撞或体积为0的情况
		if (!oa.hitable[ia] || !ob.hitable[ib] || oa.size[ia][0] == 0 || ob.size[ib][0] == 0) {
			return false;
		}
		// 碰撞数据中间变量
		boolean a_is_circle = oa.size[ia][0] > 0 && oa.size[ia][1] == 0 ? true : false;
		boolean b_is_circle = ob.size[ib][0] > 0 && ob.size[ib][1] == 0 ? true : false;
		double xa = oa.dX[ia];
		double ya = oa.dY[ia];
		double wa = oa.size[ia][0];
		double ha = oa.size[ia][1];
		double xb = ob.dX[ib];
		double yb = ob.dY[ib];
		double wb = ob.size[ib][0];
		double hb = ob.size[ib][1];
		// 是矩形则以左上角坐标计算
		// 是圆则只取w值,并除2作为半径
		xa = a_is_circle ? xa : xa - wa / 2;
		ya = a_is_circle ? ya : ya - ha / 2;
		wa = a_is_circle ? wa / 2 : wa;
		xb = b_is_circle ? xb : xb - wb / 2;
		yb = b_is_circle ? yb : yb - hb / 2;
		wb = b_is_circle ? wb / 2 : wb;

		// 执行碰撞检测
		if (a_is_circle && b_is_circle) {
			// 两者都是圆
			return circleHitCircle(xa, ya, xb, yb, wa, wb);
		} else if (!a_is_circle && !b_is_circle) {
			// 两者都是矩形
			return rectHitRect(xa, xb, ya, yb, wa, wb, ha, hb);
		} else if (!a_is_circle && b_is_circle) {
			// B为圆A为矩形
			return rectHitCircle(xa, ya, wa, ha, xb, yb, wb);
		} else {
			// A为圆B为矩形
			return rectHitCircle(xb, yb, wb, hb, xa, ya, wa);
		}
	}

	// 正圆之间碰撞检测
	private boolean circleHitCircle(double xa, double ya, double xb, double yb, double ra, double rb) {
		double rX = Math.pow((xa - xb), 2);
		double rY = Math.pow((ya - yb), 2);
		return Math.sqrt(rX + rY) <= ra + rb ? true : false;
	}

	// 无旋转矩形之间碰撞检测
	private boolean rectHitRect(double xa, double xb, double ya, double yb, double wa, double wb, double ha, double hb) {
		if (xa >= xb && xa >= xb + wb) {
			return false;
		} else if (xa <= xb && xa + wa <= xb) {
			return false;
		} else if (ya >= yb && ya >= yb + hb) {
			return false;
		} else if (ya <= yb && ya + ha <= yb) {
			return false;
		} else {
			return true;
		}
	}

	// 无旋转矩形与正圆之间碰撞检测
	private boolean rectHitCircle(double rx, double ry, double rw, double rh, double cx, double cy, double cr) {
		double disx = Math.abs(cx - (rx + rw / 2));
		double disy = Math.abs(cy - (ry + rh / 2));
		if (disx > (rw / 2 + cr)) {
			return false;
		}
		if (disy > (rh / 2 + cr)) {
			return false;
		}
		if (disx <= (rw / 2)) {
			return true;
		}
		if (disy <= (rh / 2)) {
			return true;
		}
		double discir = Math.pow(disx - rw / 2, 2) + Math.pow(disy - rh / 2, 2);
		return discir <= Math.pow(cr, 2);
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
