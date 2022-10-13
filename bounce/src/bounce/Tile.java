package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.concurrent.ThreadLocalRandom;


class Tile extends Entity {
    public int x;
    public int y;
    public int type;


    public Tile(final int x, final int y, final int tileType) {
        this.x = x;
        this.y = y;
        this.type = tileType;
        //0 - Standard tile

        //Set tile art
        if(type==0) {
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TILE_STANDARD_RSC));
        }else{
            System.out.println("Invalid type endered, defaulting to type 0");
            type=0;
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TILE_STANDARD_RSC));
        }
    }
}


