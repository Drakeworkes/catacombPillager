package bounce;

import jig.Entity;
import jig.ResourceManager;


class Player extends Entity {
    public int x;
    public int y;
    public int type;


    public Player(final int x, final int y) {
        super(x, y);
        this.x = x;
        this.y = y;


        //Set tile art
        addImageWithBoundingBox(ResourceManager
                .getImage(Game.PLAYER_STANDARD_RSC));
    }
}


