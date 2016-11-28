/**
* MyScene: Define the scene
*/

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.ArrayList;


public class MyScene {

	static MyTarget target = null;
	static ArrayList<MyBullet> bullet = new ArrayList<MyBullet>();
	static int last_shot = 50000;

	// color
	static float silver[] = { 0.5f, 0.5f, 0.5f, 0.1f };
	static float blue[] = { 0.2f, 0.2f, 0.8f, 1.0f };
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };

	// camera
	static float camera_pos[] = { 0.0f, 0.0f, 50.0f };
	static float camera_angle[] = { 0.0f, 0.0f, 0.0f };
	static float th = 0.0f;


	public static float getCameraPosx() { return camera_pos[0]; }
	public static float getCameraPosy() { return camera_pos[1]; }
	public static float getCameraPosz() { return camera_pos[2]; }

	public static float getCameraAnglex() { return camera_angle[0]; }
	public static float getCameraAngley() { return camera_angle[1]; }
	public static float getCameraAnglez() { return camera_angle[2]; }

	public static void init() {
		target = new MyTarget();
		target.setv();
	}

	static void calcCamera() {
		// float t = (float)((int)th/10)*10;
		float t = th;
		camera_pos[0] = (float)Math.cos(t/180*Math.PI)*40;
		camera_pos[1] = (float)Math.sin(t/180*Math.PI)*40;
		camera_pos[2] = 8.0f;
		camera_angle[0] = -camera_pos[0];
		camera_angle[1] = -camera_pos[1];
		camera_angle[2] = 0.0f;
	}

	public static void move_right() { th += 1.0f; th = th%360.0f; }
	public static void move_left() { th -= 1.0f; th = th%360.0f; }

	public static void shoot(){
		if (last_shot - target.getTime() > 30) {
			MyBullet b = new MyBullet();
			b.setInitPos(th);
			b.setv(camera_angle[0], camera_angle[1]);
			bullet.add(b);
			last_shot = target.getTime();
			System.out.println("MyScene.shoot: " + bullet.size());
		}
	}

	public static void draw(GLAutoDrawable drawable) {
		if(drawable == null) return;

		if (target.getTime() < 0 || target.getLife() <= 0){
			// ending
		}

		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		// light
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

		// field
		gl.glTranslated(0.0, 0.0, -1.0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, blue, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		glut.glutWireTorus(0.1f, target.getR(), 10, 100);

		// target
		gl.glPushMatrix();
		if(target != null) target.draw(drawable);
		gl.glPopMatrix();

		// bullet
		for (MyBullet b: bullet){
			gl.glPushMatrix();
			b.draw(drawable);
			gl.glPopMatrix();
		}

		calcCamera();
	}

}
