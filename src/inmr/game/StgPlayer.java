package inmr.game;

public class StgPlayer extends StgObjects {

	protected void setObjInfo() {
		resName = "player.png";
		wBlock = 5;
		hBlock = 5;
		index.set(0, 1);
		sizeW.set(0, 32);
		sizeH.set(0, 32);
	}

	void update() {
		playerControl();
	}

	private void playerControl() {
		if (Math.abs(dX.get(0) - Input.MX) < 2) {
			dX.set(0, (double) Input.MX);
		} else {
			dX.set(0, dX.get(0) + (Input.MX - dX.get(0)) / 4);
		}
		if (Math.abs(dY.get(0) - Input.MY) < 2) {
			dY.set(0, (double) Input.MY);
		} else {
			dY.set(0, dY.get(0) + (Input.MY - dY.get(0)) / 4);
		}
	}
}
