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
	int[][] size;

	StgObjects(int m) {
		max = m;
		imageIndex = new int[max];
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
		size = new int[max][2];
	}

	void reset(int i) {
		if (i >= 0 && i < max) {
			imageIndex[i] = 0;
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
			size[i][0] = 0;
			size[i][1] = 0;
		}
	}

	void copy(int from, int to) {
		if (from >= 0 && from < max && to >= 0 && to < max) {
			imageIndex[to] = imageIndex[from];
			visible[to] = visible[from];
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

}

enum MoveType {
	Normal, Round
}

enum DrawLayer {
	Back, Mid, Front, UI
}
