package bounce;
import java.util.ArrayList;

public class Levels {

    //ArrayList<Integer[][]> levelIndex = new ArrayList<Integer[][]>
    static int levelSize = 25;
    //Initialize levelData

    ArrayList<Object> levelData = new ArrayList<Object>();

    public static int loadLevels(){
        //Iterate through file and load in levels
        return 0;
    }

    public static Object[][] getLevel(int levelNum) {
        Object[][] level = new Object[levelSize][levelSize];
        //levelData[x][y] = Array length 3
        //0: Tile
        //1: Item
        //2: Entity
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                level[i][j] = new Object[3];
            }
        }

        return level;
    }


    //Levels need to be a 3D array, with the first two arrays for x and y, and the third keeping track of multiple entitites on one tile
    //Tile 0 is initialized for items and is ignored when checking for collisions


}
