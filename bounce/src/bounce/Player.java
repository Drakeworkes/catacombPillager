package bounce;

import jig.Entity;
import jig.ResourceManager;


class Player extends Entity {
    public int x;
    public int y;
    public int type;


    public Player(final int x, final int y) {
        this.x = x;
        this.y = y;


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


