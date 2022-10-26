package bounce;

import java.util.Arrays;

public class pathfinding {

    public static int[][] calcPaths(Tile[][][] level) {
        System.out.println("Calculating paths");
        int[][] pathing = new int[levelLoader.levelSize][levelLoader.levelSize];
        int pTileX=0;
        int pTileY=0;
        for (Tile[][] x : level) {//Iterate through x axis
            for (Tile[] y : x) {//Iterate through y axis
                if (y[2] != null && y[2].state == 2) {
                    pTileX = y[2].tileX;
                    pTileY = y[2].tileY;
                }
            }
        }
        //System.out.println("Player position: [" + pTileX + "][" + pTileY + "]");
        //Iterate through level, find player
        //At player location
        for(int x = 0; x<pathing.length; x++){
            for(int y = 0; y<pathing.length; y++){
                pathing[x][y]=0;
            }
        }
        pathing[pTileY][pTileX] = 0;
        pathing = getWeight(pTileY, pTileX, 1, pathing, level);
        //Recursively check all directions and save the path length. Write -1 where walls are at, and if a number exists only write over it if the saved length is less
        /*
        for(int[] x : pathing){
            System.out.print("[");
            for(int y : x){
                System.out.print(y+", ");
            }
            System.out.println("]");
        }*/
        return pathing;
    }

    public static int[][] getWeight(int tileX, int tileY, int weight, int[][] pathing, Tile[][][] level){
        //System.out.println("Pathing called on position [" +tileX+"]["+tileY+"]");
        if(level[tileY][tileX][0]==null || level[tileY][tileX][0].type!=0) {//Make sure this location isn't a wall
            //System.out.println("This isn't a wall. Check to see if our weight("+weight+") is less than the current tile's weight or if it is 0("+pathing[tileX][tileY]+")");

            if (pathing[tileX][tileY] > weight || pathing[tileX][tileY]==0) {//Check if the current position of tile's weight is greater than our version of weight
                //System.out.println("Setting pathing["+tileX+"]["+tileY+"] to weight: "+weight);
                pathing[tileX][tileY] = weight;

                if(tileX>0){//Check up
                    //System.out.println("TileX["+tileX+"] is not on a top edge, lets get the weights for moving up");
                    pathing = getWeight(tileX-1, tileY, weight+1, pathing, level);
                }
                if(tileX<pathing.length-1){//Check down
                    //System.out.println("tileX["+tileX+"] is less than pathing.length-1["+(pathing.length-1)+"]");
                    pathing = getWeight(tileX+1, tileY, weight+1, pathing, level);
                }
                if(tileY>0) {//Check left
                    //System.out.println("tileY[" + tileY + "] is not on the left edge, lets get the weights for moving left");
                    pathing = getWeight(tileX, tileY - 1, weight + 1, pathing, level);
                }
                if(tileY<pathing.length-1) {//Check right
                    //System.out.println("tileY[" + tileY + "] is less than pathing.length-1[" + (pathing.length - 1) + "]");
                    pathing = getWeight(tileX, tileY + 1, weight + 1, pathing, level);
                }
            }

        }else{//If this is a wall
            pathing[tileX][tileY] = 99;
        }
        return pathing;
    }

    //public int getWeight()


}
