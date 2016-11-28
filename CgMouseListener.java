/**
* CgMouseListener
*/

import java.awt.event.*;
import com.jogamp.opengl.util.Animator;


public class CgMouseListener implements MouseListener, MouseMotionListener {

	CgCanvas canvas;
	Animator animator;


	public CgMouseListener(CgCanvas c, Animator a) {
		canvas = c;
		animator = a;
	}

	public void mouseMoved(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
