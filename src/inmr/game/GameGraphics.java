package inmr.game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameGraphics {

	GameGraphics() {

	}

	public void update(Graphics2D backLayer, Graphics2D midLayer, Graphics2D frontLayer, Graphics2D uiLayer,
			JFrame window) {


	}

	public BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
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
