package inmr.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameGraphics {

	private int scene;
	private int timerMain = 0;

	private GameData gd;
	private Graphics2D backLayer, midLayer, frontLayer, uiLayer;
	private JFrame window;
	ArrayList<StgObjects> drawlist = new ArrayList<StgObjects>();

	void setGameData(GameData gamedata) {
		gd = gamedata;
	}

	void update(int s, Graphics2D bl, Graphics2D ml, Graphics2D fl, Graphics2D ul, JFrame win) {

		scene = s;
		backLayer = bl;
		midLayer = ml;
		frontLayer = fl;
		uiLayer = ul;
		window = win;

		imageEffects();

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

				int x = (int) obj.dX[i];
				int y = (int) obj.dY[i];
				int w = obj.size[i][1];
				int h = obj.size[i][2];
				drawKoma(drawlayer, window, //
						gd.imageStorage.get(obj.resName), obj.wBlock, obj.hBlock, obj.imageIndex[i], //
						x - w / 2, y - h / 2, //
						obj.opacity[i]);//
				drawlayer.setColor(Color.red);
				if (obj.hitable[i]) {
					if (obj.size[i][0] == 1) {
						drawlayer.drawRect(x - w / 2, y - h / 2, w, h);
					} else if (obj.size[i][0] == 2) {
						drawlayer.drawArc(x - w / 2, y - h / 2, w, h, 0, 360);
					}
				}
			}
		}

	}

	private void imageEffects() {
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
