/**
* MyScene: Define the scene
*/

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;


public class MyScene {

	static MyTarget target = null;

	// color
	static float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };
	static float blue[] = { 0.2f, 0.2f, 0.8f, 1.0f };
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };

	public static void init() {
		target = new MyTarget();
		target.setv();
	}

	public static void draw(GLAutoDrawable drawable) {
		if(drawable == null) return;

		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		// light
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

		// field
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, blue, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		glut.glutWireTorus(0.3f, 10.0f, 25, 25);

		// target
		gl.glPushMatrix();
		if(target != null) target.draw(drawable);
		gl.glPopMatrix();
	}

}
