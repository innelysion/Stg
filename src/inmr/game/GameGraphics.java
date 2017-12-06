package inmr.game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameGraphics {

	private GameData gd;
	private BufferedImage player;
	ArrayList<StgObjects> drawlist = new ArrayList<StgObjects>();

	void setGameData(GameData gamedata) {
		gd = gamedata;
	}

	void update(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer, JFrame window) {

		for (String key : gd.imageStorage.keySet()) {
			backLayer.drawImage(gd.imageStorage.get(key), 0, 0, window);
		}

		for (StgObjects obj : drawlist) {
			for (int i = 0; i < obj.max; i++) {
				Graphics2D drawlayer = null;
				switch (obj.layer) {
				case Back:
					drawlayer = backLayer;
					break;
				case Mid:
					drawlayer = midLayer;
					break;
				case Front:
					drawlayer = frontLayer;
					break;
				case UI:
					drawlayer = uiLayer;
					break;
				default:
					drawlayer = midLayer;
					break;
				}
				drawKoma(drawlayer, window, //
						gd.imageStorage.get(obj.resName), obj.wBlock, obj.hBlock, obj.index[i], //
						(int) obj.dX[i] - obj.sizeW[i] / 2, //
						(int) obj.dY[i] - obj.sizeH[i] / 2, //
						obj.opacity[i]);//
			}
		}

	}

	void drawKoma(Graphics2D g, JFrame w, BufferedImage image, int wblock, int hblock, int index, double x, double y,
			float opacity) {

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
