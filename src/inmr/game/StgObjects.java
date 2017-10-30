package inmr.game;

import java.util.ArrayList;

public abstract class StgObjects {

	private int unit = 0;
	// Image
	public String resName;
	public int wBlock = -1;
	public int hBlock = -1;
	public boolean active = true;
	public DrawLayer layer = DrawLayer.Mid;
	public ArrayList<Integer> index = new ArrayList<Integer>();
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
	// Hit
	public ArrayList<Boolean> hitable = new ArrayList<Boolean>();
	public ArrayList<Integer> sizeW = new ArrayList<Integer>();
	public ArrayList<Integer> sizeH = new ArrayList<Integer>();
	public ArrayList<Integer> roundHitRad = new ArrayList<Integer>();
	public ArrayList<Integer> boxHitW = new ArrayList<Integer>();
	public ArrayList<Integer> boxHitH = new ArrayList<Integer>();

	StgObjects() {
		addUnit(1);
	}

	public void kill(int i) {
		if (i < unit) {
			index.set(i, -1);
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
			hitable.set(i, false);
			sizeW.set(i, 0);
			sizeH.set(i, 0);
			roundHitRad.set(i, 0);
			boxHitW.set(i, 0);
			boxHitH.set(i, 0);
		}
	}

	public void addUnit(int qty) {
		unit += qty;
		for (int i = 0; i < qty; i++) {
			index.add(0);
			visible.add(false);
			opacity.add(1.0f);
			dX.add(0d);
			dY.add(0d);
			spdX.add(0d);
			spdY.add(0d);
			accX.add(0d);
			accY.add(0d);
			angle.add(0d);
			rotation.add(0d);
			hitable.add(false);
			sizeW.add(0);
			sizeH.add(0);
			roundHitRad.add(0);
			boxHitW.add(0);
			boxHitH.add(0);
		}
	}
	
	public int getUnitNumber(){
		return unit;
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
