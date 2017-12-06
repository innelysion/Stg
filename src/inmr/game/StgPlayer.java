package inmr.game;

public class StgPlayer extends StgObjects {

	StgPlayer(){
		super(1);
		resName = "player.png";
		wBlock = 5;
		hBlock = 5;
		index[0] = 1;
		sizeW[0] = 32;
		sizeH[0] = 32;
	}

	void update() {
		playerControl();
	}

	private void playerControl() {
		if (Math.abs(dX[0] - Input.MX) < 2) {
			dX[0] = Input.MX;
		} else {
			dX[0] += (Input.MX - dX[0]) / 4;
		}
		if (Math.abs(dY[0] - Input.MY) < 2) {
			dY[0] = Input.MY;
		} else {
			dY[0] += (Input.MY - dY[0]) / 4;
		}
	}
}
