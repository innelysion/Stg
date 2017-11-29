package inmr.game;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GameManager {

	GameData gd = new GameData(); // 资源管理器
	GameGraphics gg = new GameGraphics(); // 图像管理器
	
	GameManager() {
		gg.setGameData(gd);
	}

	public void updateLogic(){

	}
	
	public void updateGraphics(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer, JFrame window){
		gg.update(backLayer, midLayer, frontLayer, uiLayer, window);
	}

}
