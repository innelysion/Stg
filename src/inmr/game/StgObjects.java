package inmr.game;

import java.util.ArrayList;

public class StgObjects {

	// Image
	public int resource;
	public boolean active = true;
	public DrawLayer layer = DrawLayer.Mid;
	public int dZ = 0;
	public ArrayList<Integer> index = new ArrayList<Integer>();
	public ArrayList<Integer> wBlock = new ArrayList<Integer>();
	public ArrayList<Integer> hBlock = new ArrayList<Integer>();
	// Position
	public ArrayList<Boolean> visible = new ArrayList<Boolean>();
	public ArrayList<Float> opacity = new ArrayList<Float>();
	public ArrayList<Double> dX = new ArrayList<Double>();
	public ArrayList<Double> dY = new ArrayList<Double>();
	// Motion
	public ArrayList<Double> spdX = new ArrayList<Double>();
	public ArrayList<Double> spdY = new ArrayList<Double>();
	public ArrayList<Double> accX = new ArrayList<Double>();
	public ArrayList<Double> accY = new ArrayList<Double>();
	public ArrayList<Double> angle = new ArrayList<Double>();
	public ArrayList<Double> rotation = new ArrayList<Double>();

	StgObjects(int id) {
		this(1, id);
	}

	StgObjects(int qty, int id) {



		if (qty <= 0) {
			qty = 1;
		}

		for (int i = 0; i < qty; i++) {
			index.add(-1);
			wBlock.add(-1);
			hBlock.add(-1);

			visible.add(false);
			opacity.add(1f);
			dX.add(0d);
			dY.add(0d);

			spdX.add(0d);
			spdY.add(0d);
			accX.add(0d);
			accY.add(0d);

			angle.add(0d);
			rotation.add(0d);
		}
	}

	public void reset(int i) {
		index.set(i, -1);
		wBlock.set(i, -1);
		hBlock.set(i, -1);

		visible.set(i, false);
		opacity.set(i, 1f);
		dX.set(i, 0d);
		dY.set(i, 0d);

		spdX.set(i, 0d);
		spdY.set(i, 0d);
		accX.set(i, 0d);
		accY.set(i, 0d);

		angle.set(i, 0d);
		rotation.set(i, 0d);
	}

	public void move(int i, MoveType type) {
		switch (type.ordinal()) {
		case 0:
			spdX.set(i, spdX.get(i) + accX.get(i));
			spdY.set(i, spdY.get(i) + accY.get(i));
			dX.set(i, dX.get(i) + spdX.get(i));
			dY.set(i, dY.get(i) + spdY.get(i));
			break;
		case 1:
			spdX.set(i, spdX.get(i) + accX.get(i));
			spdY.set(i, spdY.get(i) + accY.get(i));
			dX.set(i, dX.get(i) + spdX.get(i) * Math.cos(Math.toRadians(angle.get(i))));
			dY.set(i, dY.get(i) + spdY.get(i) * Math.sin(Math.toRadians(angle.get(i))));
			angle.set(i, angle.get(i) + rotation.get(i));
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
