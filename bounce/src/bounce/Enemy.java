package bounce;

import jig.Entity;
import jig.ResourceManager;


class Enemy extends Entity {

    public int type;


    public Enemy(final int x, final int y, final int EnemyType) {
        super(x, y);
        this.type = EnemyType;
        //0 - Standard tile

        //Set tile art
        if(type==0) {
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.ENEMY_STANDARD_RSC));
        }else{
            System.out.println("Invalid type endered, defaulting to type 0");
            type=0;
            addImageWithBoundingBox(ResourceManager
                    .getImage(Game.TILE_STANDARD_RSC));
        }
    }
}


