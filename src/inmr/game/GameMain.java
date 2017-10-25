package inmr.game;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

public class GameMain {

	public static void main(String[] args) {
		// 防止头帧图像缓存出错
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameMain Game = new GameMain();
			}
		});
	}
	
	Timer timer = new Timer();
	MainUpdate mainupdate = new MainUpdate();
	
	GameMain(){
		timer.schedule(mainupdate, 17, 17);
	}
	
	class MainUpdate extends TimerTask {
		public void run() {
			
		}
	}

}
