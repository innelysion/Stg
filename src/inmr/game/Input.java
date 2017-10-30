package inmr.game;

import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class Input implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	static boolean LEFT, UP, RIGHT, DOWN, UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT;
	static boolean MLC, MMC, MRC;
	static Dir8 DIR8 = Dir8.NONE;
	static Dir4 DIR4 = Dir4.NONE;
	static int MX, MY, MW;

	private boolean MLCR, MMCR, MRCR;
	private boolean left_first = false;
	private boolean right_first = false;
	private Insets insets;

	Input(Insets i) {
		insets = i;
	}

	public void update(JFrame w) {
		MX = MouseInfo.getPointerInfo().getLocation().x - w.getLocationOnScreen().x - insets.left;
		MY = MouseInfo.getPointerInfo().getLocation().y - w.getLocationOnScreen().y - insets.top;
	}

	private void dirCheck() {
		if (LEFT && UP && RIGHT && DOWN) {
			DIR8 = Dir8.NONE;
			DIR4 = Dir4.NONE;
		} else if (LEFT && UP && RIGHT) {
			DIR8 = Dir8.UP;
			DIR4 = Dir4.UP;
		} else if (UP && RIGHT && DOWN) {
			DIR8 = Dir8.RIGHT;
			DIR4 = Dir4.RIGHT;
		} else if (RIGHT && DOWN && LEFT) {
			DIR8 = Dir8.DOWN;
			DIR4 = Dir4.DOWN;
		} else if (DOWN && LEFT && UP) {
			DIR8 = Dir8.LEFT;
			DIR4 = Dir4.LEFT;
		} else if (LEFT && UP) {
			DIR8 = Dir8.UPLEFT;
			DIR4 = Dir4.LEFT;
		} else if (UP && RIGHT) {
			DIR8 = Dir8.UPRIGHT;
			DIR4 = Dir4.RIGHT;
		} else if (RIGHT && DOWN) {
			DIR8 = Dir8.DOWNRIGHT;
			DIR4 = Dir4.RIGHT;
		} else if (DOWN && LEFT) {
			DIR8 = Dir8.DOWNLEFT;
			DIR4 = Dir4.LEFT;
		} else if (UP && DOWN) {
			DIR8 = Dir8.NONE;
			DIR4 = Dir4.NONE;
		} else if (LEFT) {
			DIR8 = Dir8.LEFT;
			DIR4 = Dir4.LEFT;
			if (!RIGHT) {
				right_first = false;
			}
			if (left_first != true && right_first != true) {
				left_first = true;
			}
			if (RIGHT && left_first) {
				DIR8 = Dir8.RIGHT;
				DIR4 = Dir4.RIGHT;
			}
		} else if (UP) {
			DIR8 = Dir8.UP;
			DIR4 = Dir4.UP;
		} else if (RIGHT) {
			DIR8 = Dir8.RIGHT;
			DIR4 = Dir4.RIGHT;
			if (!LEFT) {
				left_first = false;
			}
			if (right_first != true && left_first != true) {
				right_first = true;
			}
			if (LEFT && right_first) {
				DIR8 = Dir8.LEFT;
				DIR4 = Dir4.LEFT;
			}
		} else if (DOWN) {
			DIR8 = Dir8.DOWN;
			DIR4 = Dir4.DOWN;
		} else {
			left_first = false;
			right_first = false;
			DIR8 = Dir8.NONE;
			DIR4 = Dir4.NONE;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			LEFT = true;
			break;
		case KeyEvent.VK_UP:
			UP = true;
			break;
		case KeyEvent.VK_RIGHT:
			RIGHT = true;
			break;
		case KeyEvent.VK_DOWN:
			DOWN = true;
			break;
		case KeyEvent.VK_NUMPAD4:
			LEFT = true;
			break;
		case KeyEvent.VK_NUMPAD8:
			UP = true;
			break;
		case KeyEvent.VK_NUMPAD6:
			RIGHT = true;
			break;
		case KeyEvent.VK_NUMPAD2:
			DOWN = true;
			break;
		}
		dirCheck();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			LEFT = false;
			break;
		case KeyEvent.VK_UP:
			UP = false;
			break;
		case KeyEvent.VK_RIGHT:
			RIGHT = false;
			break;
		case KeyEvent.VK_DOWN:
			DOWN = false;
			break;
		case KeyEvent.VK_NUMPAD4:
			LEFT = false;
			break;
		case KeyEvent.VK_NUMPAD8:
			UP = false;
			break;
		case KeyEvent.VK_NUMPAD6:
			RIGHT = false;
			break;
		case KeyEvent.VK_NUMPAD2:
			DOWN = false;
			break;
		}
		dirCheck();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		MW -= e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:
			MLC = true;
			MLCR = false;
			break;
		case 2:
			MMC = true;
			MMCR = false;
			break;
		case 3:
			MRC = true;
			MRCR = false;
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:
			MLC = false;
			MLCR = true;
			break;
		case 2:
			MMC = false;
			MMCR = true;
			break;
		case 3:
			MRC = false;
			MRCR = true;
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public boolean MLCR(){
		boolean r = false;
		if (MLCR){
			MLCR = false;
			r = true;
		}
		return r;
	}
	
	public boolean MMCR(){
		boolean r = false;
		if (MMCR){
			MMCR = false;
			r = true;
		}
		return r;
	}
	
	public boolean MRCR(){
		boolean r = false;
		if (MRCR){
			MRCR = false;
			r = true;
		}
		return r;
	}

}

enum Dir4 {
	NONE, LEFT, UP, RIGHT, DOWN
}

enum Dir8 {
	NONE, LEFT, UPLEFT, UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN, DOWNLEFT
}
