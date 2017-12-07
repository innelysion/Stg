package inmr.game;

public class StgEnemy extends StgObjects {

	int[] action = new int[GameSetting.ENEMY_MAX];
	int[] lifetime = new int[GameSetting.ENEMY_MAX];
	int[] type = new int[GameSetting.ENEMY_MAX];
	int[] shootReq = new int[GameSetting.ENEMY_MAX];

	StgEnemy() {
		super(GameSetting.ENEMY_MAX);
		for (int i = 0; i < max; i++) {
			reset(i);
		}
		resName = "player.png";
		wBlock = 5;
		hBlock = 5;
		// TEST
		dX[0] = 500;
		dY[0] = 500;
		imageIndex[0] = 5;
		hitable[0] = true;
		size[0] = new int[]{2, 32, 32};
	}

	void update() {
		for (int i = 0; i < max; i++) {
			if (action[i] == 0) {
				continue;
			}
			act(i);
		}
	}

	void reset(int i) {
		super.reset(i);
		lifetime[i] = 0;
		type[i] = 0;
		action[i] = 0;
		shootReq[i] = 0;
	}

	void copy(int from, int to) {
		super.copy(from, to);
		lifetime[to] = lifetime[from];
		type[to] = type[from];
		action[to] = action[from];
		shootReq[to] = shootReq[from];
	}

	private int findAnIdle() {
		for (int i = 0; i < max; i++) {
			if (action[i] == 0) {
				return i;
			}
		}
		return max;
	}

	private void act(int i) {
		int a = EnemyData.ACTION_LIST[type[i]][action[i]];

	}
}

class EnemyData {

	final static int[][] ACTION_LIST = { //
			{ 1, 2, 3, 4 }, //
			{ 0, 0, 2, 2 } };

}