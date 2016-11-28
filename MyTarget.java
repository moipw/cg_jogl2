/**
* MyTarget
*/

import java.awt.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.Random;
import java.util.Arrays;


public class MyTarget {

	float v[] = { 0.0f, 0.0f };
	static float pos[] = { 0.0f, 0.0f };
	static float r = 10.0f;
	static int time = 30000;
	static int life = 3;
	static float speed = 0.1f;
	Random rnd;

	// color
	float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };


	float calcSize(float a, float b) {
		return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}

	// initialize
	public MyTarget() {
		// set initial speed vector
		rnd = new Random();
		v[0] = (float)Math.random()+0.000001f; v[1] = v[0]*(rnd.nextInt(2)-1);
		float size = calcSize(v[0], v[1]);
		v[0] = v[0]/size; v[1] = v[1]/size;
		System.out.println("MyTarget.setv: v = " + v[0] + ", " + v[1]);
	}

	public static float[] getPos() { return pos; }

	public static float getR() { return r; }

	public int getTime() { return time; }

	public int getLife() { return life; }
	public void deductLife() { life -= 1; }

	// update time, pos and v
	public void update() {
		time -= 1;
		float nextpos[] = { pos[0]+v[0]*speed, pos[1]+v[1]*speed };
		float nextv[] = Arrays.copyOf(v, v.length);
		while (Math.pow(nextpos[0], 2) + Math.pow(nextpos[1], 2) >= (r-1)*10) {
			int vx = 1, vy = 1;
			if ( v[0] < 0.0f ) vx = -1;
			if ( v[1] < 0.0f ) vy = -1;
			switch (rnd.nextInt(2)){
				case 0:
					nextv[0] = -1*vx; nextv[1] = -1*vy;
					break;
				case 1:
					nextv[0] = -1*vx; nextv[1] = vy;
					break;
				case 2:
					nextv[0] = vx; nextv[1] = -1*vy;
					break;
			}
			nextv[0] = nextv[0]*((float)Math.random()+0.000001f);
			nextv[1] = nextv[1]*((float)Math.random()+0.000001f);
			float size = calcSize(nextv[0], nextv[1]);
			nextv[0] = nextv[0]/size; nextv[1] = nextv[1]/size;
			nextpos[0] = pos[0]+nextv[0]*speed; nextpos[1] = pos[1]+nextv[1]*speed;
		}
		pos = nextpos;
		if (v[0] != nextv[0] || v[1] != nextv[1]){
			v = nextv;
			System.out.println("MyTarget.update: v = " + v[0] + ", " + v[1]);
		}
	}

	// draw target
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, red, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		gl.glTranslated(pos[0], pos[1], 0.0f);
		glut.glutSolidSphere(1.0f, 25, 25);

		update();
	}

}
