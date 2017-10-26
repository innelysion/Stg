package inmr.game;

import java.util.ArrayList;

public class StgObjects {

	// Image
	private String imageName = "";
	private ArrayList<Integer> index = new ArrayList<Integer>();
	private ArrayList<Integer> wBlock = new ArrayList<Integer>();
	private ArrayList<Integer> hBlock = new ArrayList<Integer>();
	// Base
	private ArrayList<Boolean> visible = new ArrayList<Boolean>();
	private ArrayList<Float> opacity = new ArrayList<Float>();
	private ArrayList<Double> dX = new ArrayList<Double>();
	private ArrayList<Double> dY = new ArrayList<Double>();
	// 速度と加速度
	private ArrayList<Double> spdX = new ArrayList<Double>();
	private ArrayList<Double> spdY = new ArrayList<Double>();
	private ArrayList<Double> accX = new ArrayList<Double>();
	private ArrayList<Double> accY = new ArrayList<Double>();
	// 角度と円心と軸(特殊移動に使う)
	private ArrayList<Double> angle = new ArrayList<Double>();
	private ArrayList<Double> cirCenterX = new ArrayList<Double>();
	private ArrayList<Double> cirCenterY = new ArrayList<Double>();
	private ArrayList<Double> axisX = new ArrayList<Double>();
	private ArrayList<Double> axisY = new ArrayList<Double>();
	private ArrayList<Double> rotation = new ArrayList<Double>();



	StgObjects() {
		this(1);
	}

	StgObjects(int qty) {
		if (qty <= 0){
			qty = 1;
		}

		for (int i = 0; i < qty; i++){
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
			cirCenterX.add(0d);
			cirCenterY.add(0d);
			axisX.add(0d);
			axisY.add(0d);
			rotation.add(0d);
		}
	}

}
