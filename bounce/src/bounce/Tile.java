package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.concurrent.ThreadLocalRandom;


class Tile extends Entity {

    public int type;
    int tileX;
    int tileY;
    int prevTileX;
    int prevTileY;
    public int desiredX;
    public int desiredY;
    public int x;
    public int y;
    public int state;


    public Tile(final int x, final int y, final int tileType, int tileState, int tileX, int tileY) {
        super(x, y);
        this.tileX = tileX;
        this.tileY = tileY;
        this.prevTileX = 0;
        this.prevTileY = 0;
        this.x = x;
        this.y = y;
        this.state = tileState;
        //0 = tile
        //1 = enemy
        //2 = player
        this.type = tileType;
        //0 - Standard tile
        //1 - Treasure
        //2 - Weapon
        //3 - Exit
        this.desiredX = tileX;
        this.desiredY = tileY;

        //Set tile art
        if (state == 0) {
            if (type == 0) {//Set as standard tile
                //STRETCH GOAL: Make multiple tile art and have it randomly set here
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            } else if (type == 1) {//Set as Treasure
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TREASURE_STANDARD_RSC));
            } else if (type == 2) {//Set as weapon
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.WEAPON_STANDARD_RSC));
            } else if (type == 3) {//Set as exit
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.EXIT_STANDARD_RSC));
            } else {
                System.out.println("Invalid type entered, defaulting to type 0");
                type = 0;
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            }
        } else if (state == 1) {
            //This is an enemy
            if (type == 0) {//Set the art
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.ENEMY_STANDARD_RSC));
            } else {
                System.out.println("Invalid type entered, defaulting to type 0");
                type = 0;
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            }
        } else if (state == 2) {
            //This is the player
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.PLAYER_STANDARD_RSC));

        } else {
            System.out.println("Wrong tile state entered");
        }
    }


    public static void movePlayer(Tile[][][] level, int direction) {
        //Direction:
        //W = 0
        //A = 1
        //S = 2
        //D = 3
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[2] != null && y[2].state == 2) {
                    if (direction == 0) {//Move up
                        y[2].desiredY = y[2].tileY - 1;
                    } else if (direction == 1) {//Move left
                        y[2].desiredX = y[2].tileX - 1;
                    } else if (direction == 2) {//Move down
                        y[2].desiredY = y[2].tileY + 1;
                    } else if (direction == 3) {//Move right
                        y[2].desiredX = y[2].tileX + 1;
                    } else {
                        System.out.print("movePlayer: Invalid direction given");
                    }
                }
            }
        }
    }

    public static void moveEnemies(Tile[][][] level, int[][]pathing){
        System.out.println("Moving enemies");
        int direction = 0;
        int bestWeight = 99;
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[1] != null && y[1].state == 1) {
                    direction = 0;
                    bestWeight = 99;

                    if(y[1].tileY>0){//Check up
                        System.out.println("Enemy at ["+(y[1].tileX)+"]["+y[1].tileY+"] is not at the top edge of screen, check up");
                        int thisWeight = pathing[y[1].tileY-1][y[1].tileX];
                        System.out.println("Tile["+(y[1].tileX)+"]["+(y[1].tileY-1)+"] has a weight of "+thisWeight);
                        if (thisWeight < bestWeight){
                            System.out.println(thisWeight +" < "+bestWeight+", We're moving up");
                            bestWeight = thisWeight;
                            direction = 0;
                        }
                    }
                    if(y[1].tileY<(pathing.length-1)){//Check down
                        System.out.println("Enemy at ["+(y[1].tileX)+"]["+y[1].tileY+"] is not at the bottom edge of screen, check down");
                        int thisWeight = pathing[y[1].tileY+1][y[1].tileX];
                        System.out.println("Tile["+(y[1].tileX)+"]["+(y[1].tileY+1)+"] has a weight of "+thisWeight);
                        if (thisWeight < bestWeight){
                            System.out.println(thisWeight +" < "+bestWeight+", We're moving down");
                            bestWeight = thisWeight;
                            direction = 2;
                        }
                    }
                    if(y[1].tileX>0){//Check Left
                        System.out.println("Enemy at ["+(y[1].tileX)+"]["+y[1].tileY+"] is not at the left edge of screen, check left");
                        int thisWeight = pathing[y[1].tileY][y[1].tileX-1];
                        System.out.println("Tile["+(y[1].tileX-1)+"]["+(y[1].tileY)+"] has a weight of "+thisWeight);
                        if (thisWeight < bestWeight){
                            System.out.println(thisWeight +" < "+bestWeight+", We're moving left");
                            bestWeight = thisWeight;
                            direction = 1;
                        }
                    }
                    if(y[1].tileX<(pathing.length-1)){//Check Right
                        System.out.println("Enemy at ["+(y[1].tileX)+"]["+y[1].tileY+"] is not at the left edge of screen, check right");
                        int thisWeight = pathing[y[1].tileY][y[1].tileX+1];
                        System.out.println("Tile["+(y[1].tileX+1)+"]["+(y[1].tileY)+"] has a weight of "+thisWeight);
                        if (thisWeight < bestWeight){
                            System.out.println(thisWeight +" < "+bestWeight+", We're moving right");
                            bestWeight = thisWeight;
                            direction = 3;
                        }
                    }

                    if (direction == 0) {//Move up
                        y[1].desiredY = y[1].tileY - 1;
                    } else if (direction == 1) {//Move left
                        y[1].desiredX = y[1].tileX - 1;
                    } else if (direction == 2) {//Move down
                        y[1].desiredY = y[1].tileY + 1;
                    } else if (direction == 3) {//Move right
                        y[1].desiredX = y[1].tileX + 1;
                    } else {
                        System.out.print("moveEnemy: Invalid direction given");
                    }

                    System.out.println("The enemy located at ["+y[1].tileX+"]["+y[1].tileY+"] Is moving in direction "+direction+" , with best weight of "+bestWeight);
                }
            }
        }
    }

    public static void updatePos(Tile[][][] level, int offset) {

        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[2] != null) {//Are there entities here?
                    if (y[2].tileX != y[2].desiredX) {
                        if (y[2].tileX > y[2].desiredX) {//Move left
                            y[2].x = ((y[2].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize)) - offset;
                        } else {//Move right
                            y[2].x = ((y[2].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize)) + offset;
                        }
                    } else if (y[2].tileY != y[2].desiredY) {
                        if (y[2].tileY > y[2].desiredY) {//Move down
                            y[2].y = ((y[2].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2) - offset;
                        } else {//Move up
                            y[2].y = ((y[2].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2) + offset;
                        }
                    }
                    //Snap to grid locations
                    if (offset == (levelLoader.tileSize+1)) {
                        y[2].prevTileX = y[2].tileX;//Save what tile we were at
                        y[2].prevTileY = y[2].tileY;//Save what tile we were at

                        y[2].tileX = y[2].desiredX;
                        y[2].tileY = y[2].desiredY;
                        y[2].x = ((y[2].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize));
                        y[2].y = ((y[2].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2);
                    }
                    y[2].update(y[2].x, y[2].y);

                }else if (y[1] != null) {//Are there entities here?
                    if (y[1].tileX != y[1].desiredX) {
                        if (y[1].tileX > y[1].desiredX) {//Move left
                            y[1].x = ((y[1].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize)) - offset;
                        } else {//Move right
                            y[1].x = ((y[1].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize)) + offset;
                        }
                    } else if (y[1].tileY != y[1].desiredY) {
                        if (y[1].tileY > y[1].desiredY) {//Move down
                            y[1].y = ((y[1].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2) - offset;
                        } else {//Move up
                            y[1].y = ((y[1].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2) + offset;
                        }
                    }
                    //Snap to grid locations
                    if (offset == (levelLoader.tileSize+1)) {
                        y[1].prevTileX = y[1].tileX;//Save what tile we were at
                        y[1].prevTileY = y[1].tileY;//Save what tile we were at

                        y[1].tileX = y[1].desiredX;
                        y[1].tileY = y[1].desiredY;
                        y[1].x = ((y[1].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize));
                        y[1].y = ((y[1].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2);
                    }
                    y[1].update(y[1].x, y[1].y);

                }
            }
        }
    }

    //Checks the local tile given a direction. Returns true if there is a collision, and false if not
    public static boolean checkTileCollision(Tile[][][] level, int direction, StateBasedGame game){
        //Direction:
        //W = 0
        //A = 1
        //S = 2
        //D = 3
        int tileX = 0;
        int tileY = 0;

        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[2] != null && y[2].state == 2) {
                    tileX = y[2].tileX;
                    tileY = y[2].tileY;
                }
            }
        }
        if (direction==0){//There is a tile above us
            if(tileY==0){//Make sure we aren't at an edge
                return true;
            }

            if(level[tileX][tileY-1][0]!=null) {//Check for tiles
                if (level[tileX][tileY - 1][0].type == 0) {//Wall
                    return true;
                }

                if (level[tileX][tileY - 1][0].type == 1) {//Treasure
                    level[tileX][tileY - 1][0] = null;//Delete the treasure from the map
                    StartUpState.points = StartUpState.points + levelLoader.treasurePoints;
                } else if (level[tileX][tileY - 1][0].type == 2) {//Weapon
                    level[tileX][tileY - 1][0] = null;//Delete the treasure from the map
                    StartUpState.points = StartUpState.points + levelLoader.weaponPoints;
                    StartUpState.weapons = StartUpState.weapons + 1;
                }
            }
            if (level[tileX][tileY - 1][1] != null) {//Check for enemies
                if (StartUpState.weapons > 0) {
                    StartUpState.weapons = StartUpState.weapons - 1;
                    level[tileX][tileY-1][1]=null;
                    //Award points based on enemy kill
                } else {
                    //You died
                    game.enterState(Game.GAMEOVERSTATE);
                }
            }

        }else if (direction==1){//Check for a tile to the left of us
            if (tileX==0){//Make sure we aren't at an edge
                return true;
            }

            if(level[tileX-1][tileY][0]!=null) {
                if (level[tileX - 1][tileY][0].type == 0) {//Wall
                    return true;
                }

                if (level[tileX - 1][tileY][0].type == 1) {//Treasure
                    level[tileX - 1][tileY][0] = null;//Delete the treasure from the map
                    StartUpState.points = StartUpState.points + levelLoader.treasurePoints;
                } else if (level[tileX - 1][tileY][0].type == 2) {//Weapon
                    level[tileX - 1][tileY][0] = null;//Delete the treasure from the map
                    StartUpState.points = StartUpState.points + levelLoader.weaponPoints;
                    StartUpState.weapons = StartUpState.weapons + 1;
                }
            }
            if (level[tileX-1][tileY][1] != null) {//Check for enemies
                if (StartUpState.weapons > 0) {
                    StartUpState.weapons = StartUpState.weapons - 1;
                    level[tileX-1][tileY][1]=null;
                    //Award points based on enemy kill
                } else {
                    //You died
                    game.enterState(Game.GAMEOVERSTATE);
                }
            }

        }else if (direction==2){//Check for a tile below us

            if (tileY==levelLoader.levelSize-1){//Check if we are at a border
                return true;
            }
            if (level[tileX][tileY+1][0]!=null && level[tileX][tileY+1][0].type==0){//Wall
                return true;
            }
            if (level[tileX][tileY+1][0]!= null && level[tileX][tileY+1][0].type == 1) {//Treasure
                level[tileX][tileY+1][0] = null;//Delete the treasure from the map
                StartUpState.points = StartUpState.points + levelLoader.treasurePoints;
                return false;
            } else if (level[tileX][tileY+1][0]!= null && level[tileX][tileY+1][0].type == 2) {//Weapon
                level[tileX][tileY+1][0] = null;//Delete the treasure from the map
                StartUpState.points = StartUpState.points + levelLoader.weaponPoints;
                StartUpState.weapons = StartUpState.weapons + 1;
                return false;
            }
            if (level[tileX][tileY + 1][1] != null) {//Check for enemies
                if (StartUpState.weapons > 0) {
                    StartUpState.weapons = StartUpState.weapons - 1;
                    level[tileX][tileY+1][1]=null;
                    //Award points based on enemy kill
                } else {
                    //You died
                    game.enterState(Game.GAMEOVERSTATE);
                }
            }
        }else if (direction==3){//Check for a tile to the right of us

            if (tileX==levelLoader.levelSize-1){//Check if we are at a border
                return true;
            }
            if (level[tileX+1][tileY][0]!=null && level[tileX+1][tileY][0].type==0) {//Wall
                return true;
            }
            if (level[tileX+1][tileY][0]!= null && level[tileX+1][tileY][0].type == 1) {//Treasure
                level[tileX+1][tileY][0] = null;//Delete the treasure from the map
                StartUpState.points = StartUpState.points + levelLoader.treasurePoints;
                return false;
            } else if (level[tileX+1][tileY][0]!= null && level[tileX+1][tileY][0].type == 2) {//Weapon
                level[tileX+1][tileY][0] = null;//Delete the treasure from the map
                StartUpState.points = StartUpState.points + levelLoader.weaponPoints;
                StartUpState.weapons = StartUpState.weapons + 1;
                return false;
            }
            if (level[tileX+1][tileY][1] != null) {//Check for enemies
                if (StartUpState.weapons > 0) {//Do we have any weapons?
                    StartUpState.weapons = StartUpState.weapons - 1;//Use it
                    level[tileX+1][tileY][1]=null;//Delete the enemy
                    //Award points based on enemy kill
                } else {//You don't have weapons. Tough luck
                    //You died
                    game.enterState(Game.GAMEOVERSTATE);
                }
            }
        }
        return false;
    }

    public void update(int x, int y){
        setPosition(x, y);
    }

    public static Tile[][][] updateMap(Tile[][][] level){
        Tile[][][] updatedLevel = new Tile[levelLoader.levelSize][levelLoader.levelSize][3];
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis\
                if (y[0] != null) {//Are there entities here?
                    updatedLevel[y[0].tileX][y[0].tileY][0] = y[0];
                }
                if (y[1] != null) {//Are there entities here?
                    updatedLevel[y[1].tileX][y[1].tileY][1] = y[1];
                }
                if (y[2] != null) {//Are there entities here?
                    updatedLevel[y[2].tileX][y[2].tileY][2] = y[2];
                }

            }
        }
        return updatedLevel;
    }


}



