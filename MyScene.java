/**
* MyScene: Define the scene
*/

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import javax.media.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.ArrayList;


public class MyScene {

	static MyTarget target = null;
	static ArrayList<MyBullet> bullet = new ArrayList<MyBullet>();
	static int last_shot = 10000;
	static float collisionDist = 1.0f;
	static boolean result = false;
	static boolean isRunning = false;
	static boolean isEnding = false;
	static float bgcolor[] = { 0.0f, 0.0f, 0.0f, 0.0f };

	// color
	static float silver[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	static float blue[] = { 0.2f, 0.2f, 0.8f, 1.0f };
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	static float brown[] = { 0.259f, 0.129f, 0.0f, 1.0f };
	static float black[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	// camera
	static float camera_pos[] = { 0.0f, 0.0f, 8.0f };
	static float camera_angle[] = { 0.0f, 0.0f, 0.0f };
	static float th = 0.0f;


	public static void startDrawing() { isRunning = true; }

	public static float[] getCameraPos() { return camera_pos; }
	public static float[] getCameraAngle() { return camera_angle; }

	public static float[] getBGcolor() { return bgcolor; }

	public static void init() {
		target = new MyTarget();
		bgcolor = white;
	}

	static void calcCamera() {
		camera_pos[0] = (float)Math.cos(th/180*Math.PI)*40;
		camera_pos[1] = (float)Math.sin(th/180*Math.PI)*40;
		// camera_angle[0] = -camera_pos[0];
		// camera_angle[1] = -camera_pos[1];
		camera_angle[0] = 0.0f;
		camera_angle[1] = 0.0f;
		camera_angle[2] = 0.0f;
	}

	static float calcDist(float[] x, float[] y){
		return (float)Math.sqrt(Math.pow(x[0]-y[0], 2) + Math.pow(x[1]-y[1], 2));
	}

	public static void move_right() {
		if (isRunning) th += 5.0f;
		th = th%360.0f;
	}
	public static void move_left() {
		if (isRunning) th -= 5.0f;
		th = th%360.0f;
	}

	public static void move_up() {
		if (camera_pos[2] < 20.0f && isRunning) camera_pos[2] += 0.5f;
	}
	public static void move_down() {
		if (camera_pos[2] > 0.0f && isRunning) camera_pos[2] -= 0.5f;
	}

	public static void shoot(){
		if ((last_shot - target.getTime() > 30 || bullet.size() < 5) && isRunning) {
			MyBullet b = new MyBullet();
			b.setInitPos(th);
			b.setv(-1*camera_pos[0], -1*camera_pos[1]);
			bullet.add(b);
			last_shot = target.getTime();
			System.out.println("MyScene.shoot: " + bullet.size());
		}
	}

	static void drawString(GL2 gl, String str, int w, int h, int x0, int y0) {
		// ! unused !
		GLUT glut = new GLUT();
		GLUgl2 glu = new GLUgl2();

		gl.glDisable(GL2.GL_LIGHTING);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, w, h, 0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();

		// draw text
		// gl.glColor3d(0.0, 0.0, 0.0);
		gl.glRasterPos2f(50, 50);
		for(int i = 0; i < str.length(); i++){
			char ic = str.toCharArray()[i];
			glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, ic);
		}

		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	static void checkCollision() {
		float o[] = { 0.0f, 0.0f };
		float tpos[] = target.getPos();
		ArrayList<MyBullet> used = new ArrayList<MyBullet>();
		for (MyBullet b: bullet) {
			float bpos[] = b.getPos();
			if (calcDist(tpos, bpos) < collisionDist){
				used.add(b);
				target.deductLife();
				target.resetv();
				System.out.println("MyScene.checkCollision: rest life = " + target.getLife());
			} else if (calcDist(o, bpos) >= target.getR()+10) {
				used.add(b);
			}
		}
		for (MyBullet b: used) {
			bullet.remove(b);
			try { b.finalize(); } catch (Throwable e){
				System.out.println("error: " + e);
			};
		}
	}

	public static void draw(GLAutoDrawable drawable) {
		if (drawable == null) return;
		if (isEnding && target.end()) {
			try { Thread.sleep(1500); } catch (InterruptedException e) {
				System.out.println("error: " + e);
			}
			System.exit(0);
		}

		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		checkCollision();

		// light
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

		// string
		// drawString(gl, "abcde!",
		// 						CgCanvas.getCanvasWidth(), CgCanvas.getCanvasHeight(),
		// 						10, 10);

		// field
		gl.glTranslated(0.0, 0.0, -2.0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, white, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		glut.glutWireTorus(0.1f, target.getR(), 10, 100);

		if ((target.getTime() < 0 || target.getLife() <= 0) && !isEnding) {
			// ending
			isEnding = true;
			if (bullet.size() > 0) bullet.get(0).stop();
			target.stop();
			if (target.getLife() > 0) bgcolor = blue;
			else {
				bgcolor = red;
				result = true;
			}
		}

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
