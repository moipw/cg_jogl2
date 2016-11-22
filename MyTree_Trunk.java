/**
* MyTree_Trunk
*/

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;


public class MyTree_Trunk {

	// color
	float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };
	float brown[] = { // #A0522D
		160.0f/255.0f,
		82.0f/255.0f,
		45.0f/255.0f,
		1.0f
	};

		/**
		* draw trunk
		*/
		public void draw(GLAutoDrawable drawable) {
			GL2 gl = drawable.getGL().getGL2();
			GLUT glut = new GLUT();

			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, brown, 0);
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

			gl.glTranslated(2.0, -3.0, 0.0);
			gl.glRotated(-90.0, 0.0, 0.0, 0.0);
			glut.glutSolidCone(0.3f,3.0f,8,10);

			// gl.glTranslated(0, 2.0, 0.0);
			// gl.glRotated(5.0, 2.0, 1.0, 0.0);
			// glut.glutSolidCone(0.3f,3.0f,8,10);
		}

	}
