package inmr.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameMain {

	public static void main(String[] args) {
		// 防止头帧图像缓存出错test
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameMain Game = new GameMain();
			}
		});
	}

	BufferStrategy bs;
	Insets insets;

	Input input;
	JFrame window = new JFrame("STG");
	Timer timer = new Timer();
	MainLoop loop = new MainLoop();

	GameData data = new GameData();
	GameGraphics gg = new GameGraphics(data);
	GameManager mgr = new GameManager(data, gg);

	GameMain() {

		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		insets = window.getInsets();
		input = new Input(insets);
		int sizeW = GameSetting.WINDOW_W + insets.left + insets.right;
		int sizeH = GameSetting.WINDOW_H + insets.top + insets.bottom;
		window.setSize(sizeW, sizeH);
		window.setLocationRelativeTo(null);

		window.createBufferStrategy(2);
		bs = window.getBufferStrategy();

		window.addMouseListener(input);
		window.addMouseMotionListener(input);
		window.addKeyListener(input);
		window.addMouseWheelListener(input);

		timer.schedule(loop, 17, 17);
	}

	class MainLoop extends TimerTask {
		public void run() {

			input.update(window);
			mgr.update();

			Graphics g = bs.getDrawGraphics();
			Graphics2D backLayer = (Graphics2D) g;
			Graphics2D midLayer = (Graphics2D) g;
			Graphics2D frontLayer = (Graphics2D) g;
			Graphics2D uiLayer = (Graphics2D) g;

			if (bs.contentsLost() == false) {
				backLayer.translate(insets.left, insets.top);
				midLayer.translate(insets.left, insets.top);
				frontLayer.translate(insets.left, insets.top);
				uiLayer.translate(insets.left, insets.top);
				backLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				midLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				frontLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				uiLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);

				gg.update(backLayer, midLayer, frontLayer, uiLayer, window);
				
				bs.show();
				backLayer.dispose();
				midLayer.dispose();
				frontLayer.dispose();
				uiLayer.dispose();
			}

		}
	}

}
