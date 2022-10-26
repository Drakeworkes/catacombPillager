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

	//How many points the player has
	static int points;

	//How many weapons the player has
	static int weapons;

	static int[][] pathing = new int[levelLoader.levelSize][levelLoader.levelSize];



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

		//Draw stats
		g.drawString("Points: " + points, 10, 30);
		g.drawString("Weapons: " + weapons, 10, 50);

		//Load the level data in
		if(loadLevel) {
			loadLevel = false;
			level = levelLoader.getLevel(0);
			System.out.println("Level loaded");
			pathing = pathfinding.calcPaths(level);
		}

		if(renderlevel){
			if(renderState > levelLoader.tileSize){//Have we gotten to the final animation state yet?
				renderlevel = false;
				renderState = 0;
				level = Tile.updateMap(level);//Update the map arrays with the new positions of everything
				pathing = pathfinding.calcPaths(level);
				Tile.checkTileCollision(level, 4, game);
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
		int xPos = 0;
		int yPos = 0;
		for(int[] x : pathing){
			yPos = 0;
			for(int y : x){
				g.drawString(""+y, (yPos*40)+200, (xPos * 40));
				yPos = yPos + 1;
			}
			xPos = xPos + 1;
		}

		if(!renderlevel) {//We're double-dipping this variable to use as a keypress debounce
			if (input.isKeyDown(Input.KEY_W ) && !Tile.checkTileCollision(level, 0, game)) {//Check if we want to move up
				Tile.movePlayer(level, 0);
				Tile.moveEnemies(level, pathing);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_A) && !Tile.checkTileCollision(level, 1, game)) {//Check if we want to move left
				Tile.movePlayer(level, 1);
				Tile.moveEnemies(level, pathing);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_S) && !Tile.checkTileCollision(level, 2, game)) {//check if we want to move right
				Tile.movePlayer(level, 2);
				Tile.moveEnemies(level, pathing);
				renderlevel = true;
			} else if (input.isKeyDown(Input.KEY_D) && !Tile.checkTileCollision(level, 3, game)) {//Check if we want to move down
				Tile.movePlayer(level, 3);
				Tile.moveEnemies(level, pathing);
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