/**
 * $B%^%&%9A`:n$K1~$8$?=hM}$N$?$a$N%/%i%9(B
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
    
    /**
     * $B%+!<%=%k$N0\F0$KBP$9$k=hM}$N%a%=%C%I(B
     */
    public void mouseMoved(MouseEvent e) {
    	
    }
    
    /**
     * $B%+!<%=%k$,%&%#%s%I%&FbIt$KF~$C$?$H$-$N%a%=%C%I(B
     */
    public void mouseEntered(MouseEvent e) {
    	
    }
    
    /**
     * $B%+!<%=%k$,%&%#%s%I%&30It$K=P$?$H$-$N%a%=%C%I(B
     */
    public void mouseExited(MouseEvent e) {
    	
    }

    /**
     * $B%^%&%9$N%/%j%C%/A`:n$N%a%=%C%I(B
     */
    public void mouseClicked(MouseEvent e) {
    	
    }
    
    /**
     * $B%^%&%9$N%I%i%C%0A`:n$N%a%=%C%I(B
     */
    public void mouseDragged(MouseEvent e) {
    	
    }
    
    /**
     * $B%^%&%9$N%\%?%s$r2!$7$?$H$-$N%a%=%C%I(B
     */
    public void mousePressed(MouseEvent e) {
    	animator.start();
    }
    
    /**
     * $B%^%&%9$N%\%?%s$rN%$7$?$H$-$N%a%=%C%I(B
     */
    public void mouseReleased(MouseEvent e) {
    	animator.stop();
    }

    
}
