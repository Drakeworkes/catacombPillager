package bounce;

import jig.Entity;
import jig.ResourceManager;


class Player extends Entity {

    public int type;
    public int desiredX;
    public int desiredY;


    public Player(final int x, final int y) {
        super(x, y);
        this.desiredX = x;
        this.desiredY = y;


        //Set tile art
        addImageWithBoundingBox(ResourceManager
                .getImage(Game.PLAYER_STANDARD_RSC));
    }
}


