/**
* MyTarget
*/

import java.awt.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.Random;
import java.util.Arrays;


public class MyBullet {

	private static int bulletnum = 0;
	private static float bulletsize = 0.5f;
	static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	static float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };

	private float v[] = { 0.0f, 0.0f };
	private float pos[] = { 0.0f, 0.0f };
	private float speed = 0.5f;

	public MyBullet() {
		bulletnum += 1;
	}


	float calcSize(float a, float b) {
		return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}

	// set speed vector
	public void setv(float x, float y) {
		float size = calcSize(x, y);
		v[0] = x/size; v[1] = y/size;
	}

	public void setInitPos(float th) {
		float r = MyTarget.getR()+5;
		pos[0] = (float)Math.cos(th/180*Math.PI)*r;
		pos[1] = (float)Math.sin(th/180*Math.PI)*r;
	}

	public static int getNum() { return bulletnum; }

	public float[] getPos() { return pos; }

	// update pos
	public void update() {
		pos[0] = pos[0]+v[0]*speed;
		pos[1] = pos[1]+v[1]*speed;
	}

	// draw target
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, black, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		gl.glTranslated(pos[0], pos[1], 0.0f);
		glut.glutSolidSphere(bulletsize, 20, 20);

		update();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			super.finalize();
		} finally {
			destruction();
		}
	}

	private void destruction() {
		bulletnum -= 1;
	}

}
