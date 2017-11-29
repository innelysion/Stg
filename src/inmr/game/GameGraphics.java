package inmr.game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameGraphics {

	GameData gd;
	private ArrayList<StgObjects> drawlist = new ArrayList<StgObjects>();
	
	public void setGameData(GameData gamedata){
		gd = gamedata;
	}

	public void update(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer,
			JFrame window) {
		for (String key : gd.imageStorage.keySet()) {
			backLayer.drawImage(gd.imageStorage.get(key), 0, 0, window);
		}
		
		backLayer.drawString(Input.DIR8.name(), 0, 0);
	}

	public void drawKoma(Graphics2D g, JFrame w, BufferedImage image, int wblock, int hblock, int index, double x,
			double y, float opacity) {

		// 一コマの幅をゲット
		int blockW = image.getWidth() / wblock;
		// 一コマの高さをゲット
		int blockH = image.getHeight() / hblock;
		// 描画したいコマの左上端座標をゲット
		int indexX = (index % wblock == 0) ? blockW * (wblock - 1) : blockW * ((index % wblock) - 1);
		int indexY = (index % wblock == 0) ? blockH * (index / wblock - 1) : blockH * (index / wblock);
		// 不透明度
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));

		// 描画
		g.drawImage(image,
				// 画面に出したいところ
				(int) (x), // 左上端X座標
				(int) (y), // 左上端Y座標
				(int) (x + blockW), // 右下端X座標
				(int) (y + blockH), // 右下端Y座標
				// 画像ファイルのどこを使う
				(int) (indexX), // 左上端X座標
				(int) (indexY), // 左上端Y座標
				(int) (indexX + blockW), // 右下端X座標
				(int) (indexY + blockH), // 右下端Y座標
				w);

		// 不透明度リセット
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));

	}

}
