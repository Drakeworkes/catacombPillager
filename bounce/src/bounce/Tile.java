package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.concurrent.ThreadLocalRandom;


class Tile extends Entity {

    public int type;
    public int desiredX;
    public int desiredY;
    public int x;
    public int y;
    public int state;


    public Tile(final int x, final int y, final int tileType, int tileState) {
        super(x, y);
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
        this.desiredX = x;
        this.desiredY = y;

        //Set tile art
        if(state == 0) {
            if (type == 0) {
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            } else if (type == 1) {
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TREASURE_STANDARD_RSC));
            } else if (type == 2) {
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.WEAPON_STANDARD_RSC));
            } else if (type == 3) {
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.EXIT_STANDARD_RSC));
            } else {
                System.out.println("Invalid type endered, defaulting to type 0");
                type = 0;
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            }
        }else if(state == 1) {
            //This is an enemy
            if(type==0) {//Set the art
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.ENEMY_STANDARD_RSC));
            }else{
                System.out.println("Invalid type endered, defaulting to type 0");
                type=0;
                addImageWithBoundingBox(ResourceManager
                        .getImage(Game.TILE_STANDARD_RSC));
            }
        }else if(state == 2){
            //This is the player
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.PLAYER_STANDARD_RSC));

        }else{
            System.out.println("Wrong tile state entered");
        }
    }

    public static int[] getPlayerLoc(Tile[][][] level){
        int[] playerLoc = new int[2];
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                for (Tile E : y) {
                    if(E.state == 1){
                        playerLoc[0] = E.x;
                        playerLoc[1] = E.y;
                    }
                }
            }
        }
        return playerLoc;
    }

}


