package bounce;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


/**
 * This state is active when the Game is over. In this state, the ball is
 * neither drawn nor updated; and a gameover banner is displayed. A timer
 * automatically transitions back to the StartUp State.
 *
 * Transitions From PlayingState
 *
 * Transitions To StartUpState
 */
class GameOverState extends BasicGameState {


	private int timer;
	private int splashY;
	private int splashX;
	private boolean splashState;
	private int lastKnownPoints; // the user's score, to be displayed, but not updated.

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 4000;
		splashY = 200;
		splashX = 250;
		splashState = false;
	}

	public void setUserScore(int points) {
		lastKnownPoints = points;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {

		Game bg = (Game)game;
		g.drawImage(ResourceManager.getImage(Game.GAMEOVER_BG_RSC),
				0, 0);
		g.drawString("Points: " + lastKnownPoints, 10, 30);

		if(splashState){
			if(splashY>=300){
				splashState = false;
			}else{
				splashY = splashY + 1;
				splashX = splashX - 2;
			}
		}else{
			if(splashY<=200) {
				splashState = true;
			}else{
				splashY = splashY - 1;
				splashX = splashX + 2;
			}
		}


		g.drawImage(ResourceManager.getImage(Game.GAMEOVER_TEXT_RSC),
				splashX, splashY);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
					   int delta) throws SlickException {


		timer -= delta;
		if (timer <= 0)
			game.enterState(Game.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );

	}

	@Override
	public int getID() {
		return Game.GAMEOVERSTATE;
	}

}