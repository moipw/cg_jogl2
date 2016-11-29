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

	private float v[] = { 0.0f, 0.0f };
	private static float pos[] = { 0.0f, 0.0f };
	private static float r = 10.0f;
	private static float targetsize = 1.0f;
	private static int time = 1500;
	private static int life = 5;
	private static float speed = 0.1f;
	private static boolean isStopping = false;
	private static boolean isEnd = false;
	private Random rnd;

	// color
	float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	float brown[] = { 0.5f, 0.3f, 0.2f, 1.0f };
	float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };



	float calcSize(float a, float b) {
		return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}

	// initialize
	public MyTarget() {
		// set initial speed vector
		rnd = new Random();
		v[0] = (float)Math.random()*(rnd.nextInt(1) == 0 ? 1.0f : -1.0f);
		v[1] = (float)Math.random()*(rnd.nextInt(1) == 0 ? 1.0f : -1.0f);
		float size = calcSize(v[0], v[1]);
		v[0] = v[0]/size; v[1] = v[1]/size;
		System.out.println("MyTarget: v = " + v[0] + ", " + v[1]);
	}

	public void resetv() {
		float nextv[] = { 0.0f, 0.0f };
		nextv[0] = -1*v[0]*((float)Math.random()+0.000001f);
		nextv[1] = -1*v[1]*((float)Math.random()+0.000001f);
		float size = calcSize(nextv[0], nextv[1]);
		v[0] = nextv[0]/size;
		v[1] = nextv[1]/size;
		System.out.println("MyTarget.resetv: v = " + v[0] + ", " + v[1]);
	}

	public static void stop() { isStopping = true; }

	public static float[] getPos() { return pos; }

	public static float getR() { return r; }

	public static boolean end() { return isEnd; }

	public int getTime() { return time; }

	public int getLife() { return life; }
	public void deductLife() { life -= 1; }


	// update time, pos and v
	public void update() {
		if (isStopping) {
			if (0.00f > targetsize || targetsize > 2.00f) isEnd = true;
			if (life > 0) targetsize += 0.01f;
			else targetsize -= 0.01;
		} else {
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
				nextv[0] = nextv[0]*((float)Math.random());
				nextv[1] = nextv[1]*((float)Math.random());
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
	}

	// draw target
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, brown, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		gl.glTranslated(pos[0], pos[1], 0.0f);
		glut.glutSolidSphere(targetsize, 25, 25);

		update();
	}

	public void ending(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		System.out.println("MyTarget.explosion: time = " + (30000 - time));
		try{ Thread.sleep(1000); } catch (InterruptedException e) {
			System.out.println("error: " + e);
		}
	}

}
