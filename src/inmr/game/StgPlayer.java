package inmr.game;

public class StgPlayer extends StgObjects {

	StgPlayer(){
		super(1);
		super.reset(0);
		resName = "player.png";
		wBlock = 5;
		hBlock = 5;
		imageIndex[0] = 1;
		size[0] = new int[]{128, 0};
		hitable[0] = true;
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
