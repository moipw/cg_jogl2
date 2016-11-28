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
	float pos[] = { 0.0f, 0.0f };
	int time = 30000;
	int life = 3;
	Random rnd;

	// color
	float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };


	// set initial speed vector
	public void setv() {
		rnd = new Random();
		v[0] = (float)Math.random()+0.000001f; v[1] = v[0]*(rnd.nextInt(2)-1);
		float size = (float)Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
		v[0] = v[0]/size; v[1] = v[1]/size;
		System.out.println("MyTarget.setv: v = " + v[0] + ", " + v[1]);
	}

	public float getPosx() {
		return pos[0];
	}

	public float getPosy() {
		return pos[1];
	}

	public int getTime() {
		return time;
	}

	public void deductLife() {
		life -= 1;
	}

	// update time, pos and v
	public void update() {
		time -= 1;
		float nextpos[] = { pos[0]+v[0]*0.2f, pos[1]+v[1]*0.2f };
		float nextv[] = Arrays.copyOf(v, v.length);
		while (Math.pow(nextpos[0], 2) + Math.pow(nextpos[1], 2) >= 90.0f){
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
			float size = (float)Math.sqrt(Math.pow(nextv[0], 2) + Math.pow(nextv[1], 2));
			nextv[0] = nextv[0]/size; nextv[1] = nextv[1]/size;
			nextpos[0] = pos[0]+nextv[0]*0.2f; nextpos[1] = pos[1]+nextv[1]*0.2f;
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
