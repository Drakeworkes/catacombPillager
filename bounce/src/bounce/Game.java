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

	public static final String BALL_BALLIMG_RSC = "bounce/resource/ball.png";
	public static final String BALL_BROKENIMG_RSC = "bounce/resource/brokenball.png";
	public static final String GAMEOVER_BANNER_RSC = "bounce/resource/gameover.png";
	public static final String STARTUP_BANNER_RSC = "bounce/resource/PressSpace.png";
	public static final String BANG_EXPLOSIONIMG_RSC = "bounce/resource/explosion.png";
	public static final String BANG_EXPLOSIONSND_RSC = "bounce/resource/explosion.wav";
	public static final String TILE_STANDARD_RSC = "bounce/resource/regTile.png";
	public static final String TILE_SHIELD_RSC = "bounce/resource/shieldTile.png";
	public static final String TILE_DBLSHIELD_RSC = "bounce/resource/dblShieldTile.png";
	public static final String TILE_SLOW_RSC = "bounce/resource/slowTile.png";
	public static final String TILE_FAST_RSC = "bounce/resource/fastTile.png";
	public static final String PADDLE_REGULAR_RSC = "bounce/resource/regPaddle.png";
	public static final String PADDLE_SHORT_RSC = "bounce/resource/regPaddle.png";
	public static final String SPLASH_SCREEN_RSC = "bounce/resource/SplashScreen.png";
	public static final String SPLASH_WORDS_RSC = "bounce/resource/spaceSplash.png";
	public static final String GAME_BG_RSC = "bounce/resource/gameBG.png";
	public static final String GAMEOVER_BG_RSC = "bounce/resource/gameoverBG.png";
	public static final String GAMEOVER_TEXT_RSC = "bounce/resource/gameOverText.png";
	public static final String BANG_LONG_RSC = "bounce/resource/expl-long.wav";
	public static final String BANG_SHORT_RSC = "bounce/resource/expl-short.wav";
	public static final String BANG_TINY_RSC = "bounce/resource/expl-tiny.wav";
	public static final String HIT_REGULAR_RSC = "bounce/resource/hit.wav";
	public static final String GAME_OVER_RSC = "bounce/resource/gameOver.wav";
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
		ResourceManager.loadSound(BANG_EXPLOSIONSND_RSC);
		ResourceManager.loadSound(BANG_LONG_RSC);
		ResourceManager.loadSound(BANG_SHORT_RSC);
		ResourceManager.loadSound(BANG_TINY_RSC);
		ResourceManager.loadSound(HIT_REGULAR_RSC);
		ResourceManager.loadSound(GAME_OVER_RSC);


		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(BALL_BALLIMG_RSC);
		ResourceManager.loadImage(BALL_BROKENIMG_RSC);
		ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		ResourceManager.loadImage(STARTUP_BANNER_RSC);
		ResourceManager.loadImage(BANG_EXPLOSIONIMG_RSC);
		ResourceManager.loadImage(TILE_STANDARD_RSC);
		ResourceManager.loadImage(TILE_SHIELD_RSC);
		ResourceManager.loadImage(TILE_DBLSHIELD_RSC);
		ResourceManager.loadImage(TILE_FAST_RSC);
		ResourceManager.loadImage(TILE_SLOW_RSC);
		ResourceManager.loadImage(PADDLE_REGULAR_RSC);
		ResourceManager.loadImage(PADDLE_SHORT_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_RSC);
		ResourceManager.loadImage(SPLASH_WORDS_RSC);
		ResourceManager.loadImage(GAME_BG_RSC);
		ResourceManager.loadImage(GAMEOVER_BG_RSC);
		ResourceManager.loadImage(GAMEOVER_TEXT_RSC);

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
