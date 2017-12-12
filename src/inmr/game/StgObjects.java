package inmr.game;

public abstract class StgObjects {

	// ImageResource
	String resName;
	int wBlock = -1;
	int hBlock = -1;
	boolean active = true;
	DrawLayer layer = DrawLayer.Mid;
	int max;
	int[] imageIndex;
	// Position
	boolean[] visotherindexle;
	float[] opacity;
	double[] dX;
	double[] dY;
	// Motion
	double[] spdX;
	double[] spdY;
	double[] accX;
	double[] accY;
	double[] m_angle;
	double[] m_rotation;
	// Hit
	boolean[] hitable;
	int[][] size;

	StgObjects(int m) {
		max = m;
		imageIndex = new int[max];
		visotherindexle = new boolean[max];
		opacity = new float[max];
		dX = new double[max];
		dY = new double[max];
		spdX = new double[max];
		spdY = new double[max];
		accX = new double[max];
		accY = new double[max];
		m_angle = new double[max];
		m_rotation = new double[max];
		hitable = new boolean[max];
		size = new int[max][2];
	}

	void reset(int i) {
		if (i >= 0 && i < max) {
			imageIndex[i] = 0;
			visotherindexle[i] = true;
			opacity[i] = 1.0f;
			dX[i] = -9999;
			dY[i] = -9999;
			spdX[i] = 0;
			spdY[i] = 0;
			accX[i] = 0;
			accY[i] = 0;
			m_angle[i] = 0;
			m_rotation[i] = 0;
			hitable[i] = false;
			size[i][0] = 0;
			size[i][1] = 0;
		}
	}

	void copy(int from, int to) {
		if (from >= 0 && from < max && to >= 0 && to < max) {
			imageIndex[to] = imageIndex[from];
			visotherindexle[to] = visotherindexle[from];
			opacity[to] = opacity[from];
			dX[to] = dX[from];
			dY[to] = dY[from];
			spdX[to] = spdX[from];
			spdY[to] = spdY[from];
			accX[to] = accX[from];
			accY[to] = accY[from];
			m_angle[to] = m_angle[from];
			m_rotation[to] = m_rotation[from];
			hitable[to] = hitable[from];
			size[to][0] = size[from][0];
			size[to][1] = size[from][1];
		}
	}

	void move(int i, MoveType type) {
		switch (type.ordinal()) {
		// 直线运动
		case 0:
			spdX[i] += accX[i];
			spdY[i] += accY[i];
			dX[i] += spdX[i];
			dY[i] += spdY[i];
			break;
		// 指定出发点,速度,方向进行圆周,椭圆,螺旋运动
		case 1:
			spdX[i] += accX[i];
			spdY[i] += accY[i];
			dX[i] += spdX[i] * Math.cos(Math.toRadians(m_angle[i]));
			dY[i] += spdY[i] * Math.sin(Math.toRadians(m_angle[i]));
			m_angle[i] += m_rotation[i];
			break;
		}
	}

	public boolean hit(int thisindex, StgObjects others, int otherindex) {
		// 不可碰撞或体积为0的情况
		if (!this.hitable[thisindex] || !others.hitable[otherindex] || this.size[thisindex][0] == 0
				|| others.size[otherindex][0] == 0) {
			return false;
		}
		// 中间变量
		boolean a_is_circle = this.size[thisindex][0] > 0 && this.size[thisindex][1] == 0 ? true : false; // 碰撞判定是圆形还是矩形
		boolean b_is_circle = others.size[otherindex][0] > 0 && others.size[otherindex][1] == 0 ? true : false;
		double xa = this.dX[thisindex]; // 中心x坐标
		double ya = this.dY[thisindex]; // 中心y坐标
		double wa = this.size[thisindex][0]; // 宽
		double ha = this.size[thisindex][1]; // 高
		double xb = others.dX[otherindex];
		double yb = others.dY[otherindex];
		double wb = others.size[otherindex][0];
		double hb = others.size[otherindex][1];
		// 执行碰撞检测
		if (a_is_circle && b_is_circle) {
			// 两者都是圆
			return circleHitCircle(xa, ya, xb, yb, wa / 2, wb / 2);
		} else if (!a_is_circle && !b_is_circle) {
			// 两者都是矩形
			return rectHitRect(xa, xb, ya, yb, wa, wb, ha, hb);
		} else if (!a_is_circle && b_is_circle) {
			// B为圆A为矩形
			return rectHitCircle(xa, ya, wa / 2, ha / 2, xb, yb, wb / 2);
		} else {
			// A为圆B为矩形
			return rectHitCircle(xb, yb, wb / 2, hb / 2, xa, ya, wa / 2);
		}
	}

	// 正圆之间碰撞检测
	private boolean circleHitCircle(double xa, double ya, double xb, double yb, double ra, double rb) {
		double disx = Math.pow((xa - xb), 2);
		double disy = Math.pow((ya - yb), 2);
		return Math.sqrt(disx + disy) <= ra + rb ? true : false;
	}

	// 无旋转矩形之间碰撞检测
	private boolean rectHitRect(double xa, double xb, double ya, double yb, double wa, double wb, double ha,
			double hb) {
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
		double disx = Math.abs(cx - rx);
		double disy = Math.abs(cy - ry);
		// 中心距离大于圆形半径+矩形宽/高的一半的情况
		if (disx > (rw + cr)) {
			return false;
		}
		if (disy > (rh + cr)) {
			return false;
		}
		// 中心距离小于矩形宽/高的一半的情况
		if (disx <= rw) {
			return true;
		}
		if (disy <= rh) {
			return true;
		}
		// 圆形位于矩形四角附近的情况
		double discir = Math.pow(disx - rw, 2) + Math.pow(disy - rh, 2);
		return discir <= Math.pow(cr, 2);
	}

}

enum MoveType {
	Normal, Round
}

enum DrawLayer {
	Back, Mid, Front, UI
}
