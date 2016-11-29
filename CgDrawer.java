/**
* CgDrawer
*/

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;


public class CgDrawer implements GLEventListener {

	GLAutoDrawable glAD;

	static float light0pos[] = { 0.0f, 3.0f, 30.0f, 1.0f };
	static float light1pos[] = { 5.0f, 3.0f, 3.0f, 1.0f };


	public void init(GLAutoDrawable drawable) {
		float silver[] = {0.5f, 0.5f, 0.5f, 1.0f};

		this.glAD = drawable;

		GL2 gl= drawable.getGL().getGL2();

		// enable Z-buffering
		gl.glEnable(GL.GL_RGBA);
		gl.glEnable(GL2.GL_DEPTH);
		gl.glEnable(GL2.GL_DOUBLE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);

		// enable two lights
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, silver, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, silver, 0);

		// set background color
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// called when window sizes are changed
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		GLUgl2 glu = new GLUgl2();

		if (height <= 0)
		height = 1;
		float h = (float) width / (float) height;

		gl.glViewport(0, 0, width, height);

		// projection transform
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(30.0, h, 1.0, 100.0);

		// geometric transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	// !!! kept to be called while animator is running !!!
	public void display(GLAutoDrawable drawable) {
		draw(drawable);
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUgl2 glu = new GLUgl2();

		// clear window
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// initialize for geometric transform
		gl.glLoadIdentity();

		// set bg color
		float color[] = MyScene.getBGcolor();
		gl.glClearColor(color[0], color[1], color[2], color[3]);

		// set view point
		float pos[] = MyScene.getCameraPos();
		float angle[] = MyScene.getCameraAngle();
		glu.gluLookAt(pos[0], pos[1], pos[2],
									angle[0], angle[1], angle[2],
									0.0, 0.0, 1.0);

		// set light position
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0pos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1pos, 0);

		MyScene.draw(drawable);
	}


	public GLAutoDrawable getGLAutoDrawable() {
		return glAD;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}

}
