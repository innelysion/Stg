package inmr.game;

public class StgEnemy extends StgObjects {

	int[] action = new int[GameSetting.ENEMY_MAX];
	int[] lifetime = new int[GameSetting.ENEMY_MAX];
	int[] type = new int[GameSetting.ENEMY_MAX];
	int[] shootReq = new int[GameSetting.ENEMY_MAX];

	StgEnemy() {
		super(GameSetting.ENEMY_MAX);
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

	private int findAnIdle() {
		for (int i = 0; i < max; i++) {
			if (action[i] == 0) {
				return i;
			}
		}
		return max;
	}

	private void act(int index) {
		int a = EnemyData.ACTION_LIST[type[index]][action[index]];

	}
}

class EnemyData {

	final static int[][] ACTION_LIST = { //
			{ 1, 2, 3, 4 }, //
			{ 0, 0, 2, 2 } };

}