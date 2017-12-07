package inmr.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameGraphics {

	CFPSMaker fpsMaker = new CFPSMaker();

	private int scene;
	private int timerMain = 0;

	private GameData gd;
	private Graphics2D backLayer, midLayer, frontLayer, uiLayer;
	private JFrame window;
	ArrayList<StgObjects> drawlist = new ArrayList<StgObjects>();

	void setGameData(GameData gamedata) {
		gd = gamedata;

		// DEBUG
		fpsMaker.setNowFPS(System.nanoTime());
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
				int w = obj.size[i][0];
				int h = obj.size[i][1];
				BufferedImage image = gd.imageStorage.get(obj.resName);
				drawKoma(drawlayer, window, image, obj.wBlock, obj.hBlock, obj.imageIndex[i], //
						x - image.getWidth() / obj.wBlock / 2, y - image.getHeight() / obj.hBlock / 2, //
						obj.opacity[i]);

				drawlayer.setColor(Color.RED);
				if (obj.hitable[i]) {
					if (obj.size[i][0] > 0 && obj.size[i][1] > 0) {
						drawlayer.drawRect(x - w / 2, y - h / 2, w, h);
					} else if (obj.size[i][0] > 0 && obj.size[i][1] == 0) {
						drawlayer.drawArc(x - w / 2, y - w / 2, w, w, 0, 360);
					}
				}
			}
		}
		timerMain++;
		// DEBUG
		fpsMaker.makeFPS();
		uiLayer.drawString(fpsMaker.getFPS() + " FPS", 10, 20);
	}

	private void imageEffects() {
		frontLayer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		AffineTransform at = new AffineTransform();
		frontLayer.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
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

class CFPSMaker {
	/**
	 * 设定动画运行多少帧后统计一次帧数
	 */
	public static final int FPS = 8;

	/**
	 * 换算为运行周期 单位: ns(纳秒)
	 */
	public static final long PERIOD = (long) (1.0 / FPS * 1000000000);
	/**
	 * FPS最大间隔时间，换算为1s = 10^9ns 单位: ns
	 */
	public static long FPS_MAX_INTERVAL = 1000000000L;

	/**
	 * 实际的FPS数值
	 */
	private double nowFPS = 0.0;

	/**
	 * FPS累计用间距时间 in ns
	 */
	private long interval = 0L;
	private long time;
	/**
	 * 运行桢累计
	 */
	private long frameCount = 0;

	/**
	 * 格式化小数位数
	 */
	private DecimalFormat df = new DecimalFormat("0.0");

	/**
	 * 制造FPS数据
	 *
	 */
	public void makeFPS() {
		frameCount++;
		interval += PERIOD;
		// 当实际间隔符合时间时。
		if (interval >= FPS_MAX_INTERVAL) {
			// nanoTime()返回最准确的可用系统计时器的当前值，以毫微秒为单位
			long timeNow = System.nanoTime();
			// 获得到目前为止的时间距离
			long realTime = timeNow - time; // 单位: ns
			// 换算为实际的fps数值
			nowFPS = ((double) frameCount / realTime) * FPS_MAX_INTERVAL;

			// 变更数值
			frameCount = 0L;
			interval = 0L;
			time = timeNow;
		}
	}

	public long getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(long frameCount) {
		this.frameCount = frameCount;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public double getNowFPS() {
		return nowFPS;
	}

	public void setNowFPS(double nowFPS) {
		this.nowFPS = nowFPS;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getFPS() {
		return df.format(nowFPS);
	}
}