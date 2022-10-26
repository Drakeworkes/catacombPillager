package bounce;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends StateBasedGame {

	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;

	public static final String TILE_STANDARD_RSC = "bounce/resource/regTile.png";
	public static final String EXIT_STANDARD_RSC = "bounce/resource/exitTile.png";
	public static final String ENEMY_STANDARD_RSC = "bounce/resource/enemy.png";
	public static final String ENEMY_SLEEP_RSC = "bounce/resource/enemySleep.png";
	public static final String ENEMY_AWAKE_RSC = "bounce/resource/enemyAwake.png";
	public static final String WEAPON_STANDARD_RSC = "bounce/resource/weapon.png";
	public static final String PLAYER_STANDARD_RSC = "bounce/resource/player.png";
	public static final String TREASURE_STANDARD_RSC = "bounce/resource/treasure.png";

	//public static final String BANG_LONG_RSC = "bounce/resource/expl-long.wav";
	public final int ScreenWidth;
	public final int ScreenHeight;

	//Ball ball;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 *
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public Game(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());

		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		//ResourceManager.loadSound(BANG_EXPLOSIONSND_RSC);



		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(TILE_STANDARD_RSC);
		ResourceManager.loadImage(EXIT_STANDARD_RSC);
		ResourceManager.loadImage(ENEMY_STANDARD_RSC);
		ResourceManager.loadImage(ENEMY_SLEEP_RSC);
		ResourceManager.loadImage(ENEMY_AWAKE_RSC);
		ResourceManager.loadImage(PLAYER_STANDARD_RSC);
		ResourceManager.loadImage(WEAPON_STANDARD_RSC);
		ResourceManager.loadImage(TREASURE_STANDARD_RSC);

		//ball = new Ball(ScreenWidth / 2, ((ScreenHeight / 4)*3)-15, 0f, 0f);

		//paddle = new Paddle(ScreenWidth/2, (ScreenHeight/4)*3, 0f);

	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Catacomb Pillager", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}


}
