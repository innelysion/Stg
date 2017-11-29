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
		// 防止第一帧图像缓存出错
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameMain Game = new GameMain();
			}
		});
	}

	BufferStrategy bs; // 图像缓存
	Insets insets; // 窗体边框
	Input input = new Input(); // 输入系统
	JFrame window = new JFrame("STG"); // JFrame窗体
	Timer timer = new Timer(); // 定时器
	MainLoop loop = new MainLoop(); // 主循环定时任务

	// 游戏管理器
	GameManager mgr = new GameManager(); 

	GameMain() {

		// 窗体设置
		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		insets = window.getInsets();
		int sizeW = GameSetting.WINDOW_W + insets.left + insets.right;
		int sizeH = GameSetting.WINDOW_H + insets.top + insets.bottom;
		window.setSize(sizeW, sizeH);
		window.setLocationRelativeTo(null);

		// 图像缓存
		window.createBufferStrategy(2);
		bs = window.getBufferStrategy();

		// 输入系统应用至窗体
		window.addMouseListener(input);
		window.addMouseMotionListener(input);
		window.addKeyListener(input);
		window.addMouseWheelListener(input);

		// 间隔17毫秒(近似60fps)主循环
		timer.schedule(loop, 17, 17);
	}

	class MainLoop extends TimerTask {
		public void run() {

			// 更新输入
			input.update(window);
			// 更新游戏管理器主逻辑
			mgr.updateLogic();
			// 四层描绘层
			Graphics g1 = bs.getDrawGraphics();
			Graphics g2 = bs.getDrawGraphics();
			Graphics g3 = bs.getDrawGraphics();
			Graphics g4 = bs.getDrawGraphics();
			Graphics2D backLayer = (Graphics2D) g1;
			Graphics2D midLayer = (Graphics2D) g2;
			Graphics2D frontLayer = (Graphics2D) g3;
			Graphics2D uiLayer = (Graphics2D) g4;

			if (bs.contentsLost() == false) {
				// 描绘准备
				backLayer.translate(insets.left, insets.top);
				midLayer.translate(insets.left, insets.top);
				frontLayer.translate(insets.left, insets.top);
				uiLayer.translate(insets.left, insets.top);
				backLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				midLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				frontLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				uiLayer.clearRect(0, 0, GameSetting.WINDOW_W, GameSetting.WINDOW_H);
				// 更新图像逻辑并描绘图像
				mgr.updateGraphics(backLayer, midLayer, frontLayer, uiLayer, window);
				bs.show();
				// 描绘完毕释放当前帧
				backLayer.dispose();
				midLayer.dispose();
				frontLayer.dispose();
				uiLayer.dispose();
			}

		}
	}

}
