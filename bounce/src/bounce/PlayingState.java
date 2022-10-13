package bounce;

import java.util.Iterator;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the ball using the WAS & D keys.
 *
 * Transitions From StartUpState
 *
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {

	//float homePos;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {

		//homePos = 0;	//Int used for paddleShift ability
		//Load the game level

		container.setSoundOn(true);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {
		Game bg = (Game)game;
		Input input = container.getInput();

		//Check if we need to animate
		//Check for collisions

		//Check for player input

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
					   int delta) throws SlickException {

		Input input = container.getInput();
		Game bg = (Game)game;
	}

	@Override
	public int getID() {
		return Game.PLAYINGSTATE;
	}

}