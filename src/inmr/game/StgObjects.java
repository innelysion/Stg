package inmr.game;

public abstract class StgObjects {

	private int unit = 0;
	// Image
	String resName;
	int wBlock = -1;
	int hBlock = -1;
	boolean active = true;
	DrawLayer layer = DrawLayer.Mid;
	int max;
	int[] index;
	// Position
	boolean[] visible;
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
	int[] sizeW;
	int[] sizeH;
	int[] boxHitW;
	int[] boxHitH;

	StgObjects(int m) {
		max = m;
		index = new int[max];
		visible = new boolean[max];
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
		sizeW = new int[max];
		sizeH = new int[max];
		boxHitW = new int[max];
		boxHitH = new int[max];
		for (int i = 0; i < max; i++) {
			reset(i);
		}
	}

	void reset(int i) {
		if (i >= 0 && i < max) {
			index[i] = 0;
			visible[i] = true;
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
			sizeW[i] = 0;
			sizeH[i] = 0;
			boxHitW[i] = 0;
			boxHitH[i] = 0;
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
}

enum MoveType {
	Normal, Round
}

enum DrawLayer {
	Back, Mid, Front, UI
}
