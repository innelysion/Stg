package inmr.game;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GameManager {

	private GameData gd = new GameData(); // 资源管理器
	private GameGraphics gg = new GameGraphics(); // 图像管理器
	private StgPlayer player = new StgPlayer();

	GameManager() {
		gg.setGameData(gd);
		gg.drawlist.add(player);
	}

	public void updateLogic(){
		player.update();
	}

	public void updateGraphics(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer, JFrame window){
		gg.update(backLayer, midLayer, frontLayer, uiLayer, window);
	}

}
