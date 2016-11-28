/**
* CgMain: contains the Main method
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.Animator;


public class CgMain extends JApplet {
	
	static int width = 1000;
	static int height = 800;
	static Animator animator;


	public void init() {
		// set window size
		setSize(new Dimension(width, height));

		// set canvas
		CgCanvas canvas = new CgCanvas(width, height);
		canvas.requestFocus();
		GLCanvas glc = canvas.getGLCanvas();
		animator = new Animator(glc);

		// define the layout of window
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(glc, BorderLayout.CENTER);

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(panel, BorderLayout.CENTER);

		// set key and mouse listener
		CgKeyListener mkl = new CgKeyListener(canvas, animator);
		canvas.addKeyListener(mkl);
		glc.addKeyListener(mkl);
		CgMouseListener mml = new CgMouseListener(canvas, animator);
		canvas.addMouseListener(mml);
		glc.addMouseListener(mml);

		MyScene.init();
	}

	public void start() {}

	public void stop() {}


	// main
	public static void main(String[] args) {
		JFrame frame = new JFrame("--- SHOOTING GAME ---");
		frame.setSize(width, height);
		CgMain cgmain = new CgMain();

		cgmain.init();
		frame.getContentPane().add(cgmain);
		frame.setVisible(true);

		cgmain.start();

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
	}

}
