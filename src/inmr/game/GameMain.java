package inmr.game;

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

	Input input = new Input();
	JFrame window = new JFrame("STG");
	Timer timer = new Timer();
	MainLoop loop = new MainLoop();

	GameGraphics gg = new GameGraphics();
	GameManager mgr = new GameManager(gg);

	GameMain() {

		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		insets = window.getInsets();
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
			Graphics2D backLayer = (Graphics2D) g; // Background
			Graphics2D midLayer = (Graphics2D) g; // Main
			Graphics2D frontLayer = (Graphics2D) g; // Effects
			Graphics2D uiLayer = (Graphics2D) g; // UI

			if (bs.contentsLost() == false) {
				backLayer.translate(insets.left, insets.top);
				midLayer.translate(insets.left, insets.top);
				frontLayer.translate(insets.left, insets.top);

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
