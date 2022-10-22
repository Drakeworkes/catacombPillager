package bounce;

import java.util.Iterator;
import jig.Entity;
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

	//Trigger a new level load
	boolean loadLevel;

	//Trigger a map render
	boolean renderlevel;

	//Keep track of animation frames
	int renderState = 0;



	Tile[][][] level;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		loadLevel = true;
		levelLoader.loadLevels();//Load the levels
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {

		Input input = container.getInput();
		Game bg = (Game)game;

		//Load the level data in
		if(loadLevel) {
			loadLevel = false;
			level = levelLoader.getLevel(0);
			System.out.println("Level loaded");
		}

		if(renderlevel){
			if(renderState > levelLoader.tileSize){//Have we gotten to the final animation state yet?
				renderlevel = false;
				renderState = 0;
			}else{

				renderState = renderState + 1;
				Tile.updatePos(level, renderState);
			}
		}

		//Render our scene
		if(level != null) {
			for (Tile[][] x : level) {//Iterate through x axis
				for (Tile[] y : x) {//Iterate through y axis
					for (Tile E : y) {
						if (E != null) {
							E.render(g);//Go through and render everything
						}
					}
				}
			}
		}

		if(!renderlevel) {//We're double-dipping this variable to use as a keypress debounce
			if (input.isKeyDown(Input.KEY_W)) {//Check if we want to move up
				Tile.movePlayer(level, 0);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_A)) {//Check if we want to move left
				Tile.movePlayer(level, 1);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_S)) {//check if we want to move right
				Tile.movePlayer(level, 2);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_D)) {//Check if we want to move down
				Tile.movePlayer(level, 3);
				renderlevel = true;
			}
		}

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