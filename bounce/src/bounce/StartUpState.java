package bounce;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This state is active prior to the Game starting. In this state, sound is
 * turned off, and the bounce counter shows '?'. The user can only interact with
 * the game by pressing the SPACE key which transitions to the Playing State.
 * Otherwise, all game objects are rendered and updated normally.
 *
 * Transitions From (Initialization), GameOverState
 *
 * Transitions To PlayingState
 */
class StartUpState extends BasicGameState {

	//int splashY;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//splashY = 200;
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {
		Game bg = (Game)game;

		//Check if we need to animate
		//check for collisions once we are done animating
		//If we hit a level start up the game at that level

		//Draw map
		//Draw player
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
					   int delta) throws SlickException {

		Input input = container.getInput();
		Game bg = (Game)game;

		//Get player inputs
		if (input.isKeyDown(Input.KEY_SPACE))
			bg.enterState(Game.PLAYINGSTATE);

	}

	@Override
	public int getID() {
		return Game.STARTUPSTATE;
	}

}