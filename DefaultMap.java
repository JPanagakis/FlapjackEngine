/**
 * Created by justin on 9/9/16.
 */
public class DefaultMap extends Map {

    private int[][] visualMap = {
            {
                    0,0,0,0,0,0,0,0,0,0,
                    0,1,1,1,0,0,0,0,0,0,
                    0,1,0,0,0,0,0,0,0,0,
                    0,1,1,0,0,0,0,0,0,0,
                    0,1,0,0,0,1,1,1,1,0,
                    0,1,0,0,0,0,0,1,0,0,
                    0,0,0,0,0,0,0,1,0,0,
                    0,0,0,0,1,0,0,1,0,0,
                    0,0,0,0,0,1,1,0,0,0,
                    0,0,0,0,0,0,0,0,0,0
            }
    };

    private int[][] logicMap = {
            {
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N,
                    N,N,N,N,N,N,N,N,N,N
            }
    };

    public DefaultMap(){
        super();

        tileWidth = 10;
        tileSet = "Tile";

        setupJackStart(5,5,0);

        setupMaps(visualMap, logicMap);
    }
}
