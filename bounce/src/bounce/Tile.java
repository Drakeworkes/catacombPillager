package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.concurrent.ThreadLocalRandom;


class Tile extends Entity {

    private Vector velocity;
    public int type;


    public Tile(final int x, final int y, final int tileType) {
        super(x, y);
        //this.x = x;
        //this.y = y;
        this.type = tileType;
        //0 - Standard tile
        //1 - Treasure
        //2 - Weapon
        //3 - Exit

        //Set tile art
        if (type == 0) {
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TILE_STANDARD_RSC));
        } else if (type == 1) {
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TREASURE_STANDARD_RSC));
        } else if (type == 2) {
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.WEAPON_STANDARD_RSC));
        }else if (type == 3){
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.EXIT_STANDARD_RSC));
        }else {
            System.out.println("Invalid type endered, defaulting to type 0");
            type = 0;
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TILE_STANDARD_RSC));
        }

    }

}


