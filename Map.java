import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by justin on 5/19/16.
 */
public class Map {

    private final double SCALE_X = OptionsManager.get().getScaleRatioX();
    private final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    protected MapGenerator mapGenerator;

    //Standard Tile Types
    protected final int N = 100; //No Type
    protected final int F = 101; //Floor
    protected final int D = 102; //Dangerous Floor
    protected final int W = 103; //Wall
    protected final int J = 104; //Jumpable
    protected final int C = 105; //Change Map

    //Interactive Obstacle Types
    protected final int M = 201; //Movable
    protected final int V = 202; //Vendor

    protected int tileWidth;
    protected String tileSet;

    protected ArrayList<Sprite> floorTileMap;
    protected ArrayList<Sprite> midTileMap;
    protected ArrayList<Sprite> highTileMap;

    protected ArrayList<Sprite>[] layers;

    protected int jackStartX;
    protected int jackStartY;
    protected int jackStartLayer;

    protected LinkedList<String> mapChangeCodes;

    public Map(){

        mapGenerator = new MapGenerator();
        floorTileMap = new ArrayList<>();
        midTileMap = new ArrayList<>();
        highTileMap = new ArrayList<>();

        layers = new ArrayList[10];
        setupLayers();

        mapChangeCodes = new LinkedList<>();
    }

    public void setupLayers(){
        for (int i = 0; i < layers.length; i++){
            layers[i] = new ArrayList<>();
        }
    }

    public void setupMaps(int[][] visualLayers, int[][] logicLayers){
        for (int i = 0; i < visualLayers.length; i++){
            layers[i] = mapGenerator.generate(visualLayers[i], tileWidth, tileSet, logicLayers[i], mapChangeCodes);
        }
    }

    public ArrayList<Sprite>[] getLayers(){return layers;}



    public void setup(int[] floorMap, int[] midMap, int[] typeMap, LinkedList<String> mapChangeCodes){

        this.floorTileMap = mapGenerator.generate(floorMap, tileWidth, tileSet);
        this.midTileMap = mapGenerator.generate(midMap, tileWidth, tileSet, typeMap, mapChangeCodes);
    }

    public void setup(int[] floorMap, int[] midMap, int[] typeMap, int[] highMap, int[] highTypeMap, LinkedList<String> mapChangeCodes){

        floorTileMap = mapGenerator.generate(floorMap, tileWidth, tileSet);
        midTileMap = mapGenerator.generate(midMap, tileWidth, tileSet, typeMap, mapChangeCodes);
        highTileMap = mapGenerator.generate(highMap, tileWidth, tileSet, highTypeMap, mapChangeCodes);
    }

    public ArrayList<Sprite> getFloorTileMap(){
        return floorTileMap;
    }

    public ArrayList<Sprite> getMidTileMap(){
        return midTileMap;
    }

    public ArrayList<Sprite> getHighTileMap(){
        return highTileMap;
    }

    public int getTileWidth(){
        return tileWidth;
    }

    public void setupJackStart(int x, int y, int z){
        jackStartX = (int)((50 * SCALE_X) * x);
        jackStartY = (int)((50 * SCALE_Y) * y);
        jackStartLayer = z;
    }

    public float getJackStartX(){
        return (float) jackStartX;
    }
    public float getJackStartY(){
        return (float) jackStartY;
    }
    public int getJackStartLayer(){return jackStartLayer;}
}
