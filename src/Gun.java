import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Gun implements UpdatingObject {
	private ArrayList<GameObject> bullets;
	private Image bullet;
	private int acceleration;
	public Gun(Image bullet) {
		bullets = new ArrayList<GameObject>();
		this.bullet=bullet;
		this.acceleration=3000;

	}
	public Gun(Image bullet, int acceleration) {
		this(bullet);
		this.acceleration=acceleration;
	}
	private void addBullet(Projectile b){
		bullets.add(b);
	}
	public ArrayList<GameObject> getMyLasers(){
		return bullets;
	}


	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		for (GameObject b : bullets) {
			b.render(gc, sbg, g);
		}
		
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		for(GameObject p:bullets){
			p.update(gc, sbg, delta);
		}
	}
	
	public void shoot(float beamx, float beamy, float posRotation) throws SlickException{
		System.out.println("test:"+new Vector2f(posRotation).getNormal().scale(10));
		addBullet(new Projectile(new Vector2f(beamx, beamy),new Vector2f(posRotation).scale(50),bullet.copy(), this, acceleration));
	}
	@Override
	public void renderDEBUG(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// TODO Auto-generated method stub
		
	}

}

