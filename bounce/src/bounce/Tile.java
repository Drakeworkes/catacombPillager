package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Graphics;

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

    public static void updatePos(Tile[][][] level, int offset) {
        //this.x = (x*levelLoader.tileSize)+(levelLoader.tileSize/2+levelLoader.infoSize);//Set up X coordinate in pixels
        //this.y = (y*levelLoader.tileSize)+levelLoader.tileSize/2;
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
                        y[2].prevTileX = y[2].tileX;
                        y[2].prevTileY = y[2].tileY;

                        y[2].tileX = y[2].desiredX;
                        y[2].tileY = y[2].desiredY;
                        y[2].x = ((y[2].tileX * levelLoader.tileSize) + (levelLoader.tileSize / 2 + levelLoader.infoSize));
                        y[2].y = ((y[2].tileY * levelLoader.tileSize) + levelLoader.tileSize / 2);
                    }
                    y[2].update(y[2].x, y[2].y);

                }
            }
        }
    }

    //Checks the local tile given a direction. Returns true if there is a collision, and false if not
    public static boolean checkTileCollision(Tile[][][] level, int direction){
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
        if (direction==0 && ( level[tileX][tileY-1][0]!=null || tileY==0)){//There is a tile above us
            //check if there is a weapon or treasure
            //Check if there is an enemy
            return true;
        }else if (direction==1 && ( level[tileX-1][tileY][0]!=null || tileX==0)){//Check for a tile to the left of us
            //check if there is a weapon or treasure
            //Check if there is an enemy
            return true;
        }else if (direction==2){//Check for a tile below us
            //check if there is a weapon or treasure
            //Check if there is an enemy
            if (tileY==levelLoader.levelSize-1){//Check if we are at a border
                return true;
            }
            if (level[tileX][tileY+1][0]!=null){//Check if there is a title
                return true;
            }
        }else if (direction==3){//Check for a tile to the right of us
            //check if there is a weapon or treasure
            //Check if there is an enemy
            if (tileX==levelLoader.levelSize-1){//Check if we are at a border
                return true;
            }
            if (level[tileX+1][tileY][0]!=null) {//Check if there is a tile there
                return true;
            }
        }
        return false;
    }

    public void update(int x, int y){
        setPosition(x, y);
    }

    public void updateMap(Tile[][][] level){
        Tile[][][] updatedLevel = new Tile[levelLoader.levelSize][levelLoader.levelSize][3];
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[2] != null) {//Are there entities here?
                    updatedLevel[y[2].tileX][y[2].tileY][2] = y[2];
                }
            }
        }
        level = updatedLevel;
    }


}



