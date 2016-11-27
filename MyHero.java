/**
* MyTree
*/

import java.awt.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;


public class MyHero {

	// color
	float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };
	float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };

	/**
	* draw Hero
	*/
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();
		float init_x = 2.0f, init_y = -3.0f, init_z = 0.0f;

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, red, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		// draw Hero
		gl.glRotated(-90.0, 0.0, 0.0, 0.0);
		glut.glutSolidSphere(1.0f, 25, 25);
		gl.glTranslated(0.0f, 1.0f, 0.0f);
		glut.glutSolidSphere(0.9f, 25, 25);

	}
}
