import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Starship extends ExplodingGameObject {

	static final int maxSpeed = 75;
	static final float acceleration = (float) 1.33;
	static final float velocityDecay = (float) .99;
	static final int shotDelay = 3000;

	private boolean accelerating, turningLeft, turningRight;
	private Image EnginesOff;
	private Image EnginesOn;
	Gun guns;

	// Default position at the center of the screen
	public Starship(String config) throws SlickException {
		super();
		Properties template = new Properties();
		try {
			template.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		guns = new Gun(new Image("res/Beam1.png"));
		width = Integer.parseInt(template.getProperty("width"));
		height = Integer.parseInt(template.getProperty("height"));
		EnginesOff = new Image(template.getProperty("EnginesOff"));
		EnginesOn = new Image(template.getProperty("EnginesOn"));
		ObjectImage = EnginesOff;
		alive = true;
		collisionModel = new Circle(pos.getX(), pos.getY(), height / 2);
	}

	// RENDER
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		if (alive) {
			guns.render(gc, sbg, g);
		}
	}

	private void SetImage() {
		int v = (int) Math.ceil(Math.random() - .1);
		if (SetupClass.isDEBUGGING)
			System.out.println("Engines on:" + (accelerating && v == 1));
		if (accelerating && v == 1)
			setObjectImage(EnginesOn);
		else
			setObjectImage(EnginesOff);
	}

	// UPDATE
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		guns.update(gc, sbg, delta);
		if (alive) {
			System.out.println(getRotation());
			if (turningLeft)
				rotate((float) -.22 * delta);
			if (turningRight)
				rotate((float) .22 * delta);
			if (accelerating && speed.length() < maxSpeed)
				speed = speed.add(new Vector2f(getRotation())
						.scale(acceleration - 1));
			if (!accelerating)
				speed = speed.scale(velocityDecay);
			checkForCollision();
			SetImage();
			turningLeft = false;
			accelerating = false;
			turningRight = false;
		}
		super.update(gc, sbg, delta);
	}

	public void shoot() {
		try {
			guns.shoot(pos.getX() + width / 2, pos.getY() + height / 2,
					getRotation());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void ForwardKeyPressed() {
		accelerating = true;
	}

	public void LeftKeyPressed() {
		turningLeft = true;
	}

	public void RightKeyPressed() {
		turningRight = true;
	}

	public void BackKeyPressed() {

	}

	public ArrayList<GameObject> getProjectiles() {
		return guns.getMyLasers();
	}

	@Override
	protected boolean checkForCollision() {
		if (alive) {
			System.out.println(Active);
			super.checkForCollision();
			collisionModel.setX(pos.getX() + height / 4);
			collisionModel.setY(pos.getY());
			// TODO Auto-generated method stub
			ArrayList<GameObject> Active = Play.getAsteroids();
			Iterator<GameObject> i = Active.iterator();
			while (i.hasNext()) {
				Asteroid b = (Asteroid) i.next();
				if (isCollidingWith(b) && b.isAlive()) {
					return true;
				}
			}return false;
		}else{
		return true;
		}
	}
	protected void die() {
		System.out.println("dying");
		super.die();
		if(!Active)Play.GameOver();
	}

	public String toString() {
		return "Player Starship";
	}
}
