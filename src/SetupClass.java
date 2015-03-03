import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class SetupClass extends StateBasedGame{
	public static final int play = 0;
	public static final boolean isDEBUGGING=false;
	
	
	
	static int ScreenHeight;
	static int ScreenWidth;
	public static void main(String[] args) throws SlickException{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		ScreenWidth = gd.getDisplayMode().getWidth();
		ScreenHeight = gd.getDisplayMode().getHeight();
		AppGameContainer app = new AppGameContainer(new SetupClass("setup test2"));
		app.setDisplayMode(ScreenWidth, ScreenHeight, false);
		app.setShowFPS(true);
		app.setTargetFrameRate(60);
		app.start();
	}
	
	public SetupClass(String string) {
		super(string);
		this.addState(new Play(play));
	}

	@Override
	public void initStatesList(GameContainer app) throws SlickException {
		this.getState(play).init(app, this);
		this.enterState(play);
	}

}
