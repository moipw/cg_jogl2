/**
* CgKeyListener
*/

import java.awt.event.*;
import com.jogamp.opengl.util.Animator;


public class CgKeyListener implements KeyListener {

	CgCanvas canvas;
	Animator animator;


	public CgKeyListener(CgCanvas c, Animator a) {
		canvas = c;
		animator = a;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			case KeyEvent.VK_S:
				animator.start();
				break;
		}

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_Q:
				System.exit(0);
		}
	}
}
