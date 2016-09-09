import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by justin on 5/15/16.
 */
public class MapGenerator {

    private final double SCALE_X = OptionsManager.get().getScaleRatioX();
    private final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    private int totalNumberOfTiles;
    private Tile[] map;

    private final int WIDTH = (int)(50 * SCALE_X);
    private final int HEIGHT = (int)(50 * SCALE_Y);

    private ListIterator<String> mapChangeCodes;

    public MapGenerator(){
        allImages = new ArrayList<>();
    }

    public ArrayList<Sprite> generate(int[] mapCodes, int numberOfTilesWide, String sceneCode,
                                      int[] mapTypes, LinkedList<String> mapChangeCodes){

        loadUsedImages(mapCodes, sceneCode);
        this.mapChangeCodes = mapChangeCodes.listIterator();
        Tile[] tiles = generateMap(mapCodes, numberOfTilesWide, sceneCode);
        generateTileTypes(tiles, mapTypes);
        return generateTileMap(tiles);
    }

    public ArrayList<Sprite> generate(int[] mapCodes, int numberOfTilesWide, String sceneCode){
        loadUsedImages(mapCodes, sceneCode);
        return generateTileMap(generateMap(mapCodes, numberOfTilesWide, sceneCode));
    }

    public Tile[] generateMap(int[] mapCodes, int numberOfTilesWide, String sceneCode){

        totalNumberOfTiles = mapCodes.length;
        map = new Tile[totalNumberOfTiles];

        for (int i = 0; i < totalNumberOfTiles; i++){
            //map[i] = new Tile(mapCodes[i], sceneCode, generateX(i, numberOfTilesWide), generateY(i, numberOfTilesWide));
            map[i] = new Tile(allImages.get(mapCodes[i]), generateX(i, numberOfTilesWide), generateY(i, numberOfTilesWide));
            map[i].setWidth(allImages.get(mapCodes[i]).getWidth());
            map[i].setHeight(allImages.get(mapCodes[i]).getHeight());
            adjustXY(map[i]);
        }

        return map;
    }

    public void adjustXY(Tile tile){
        int width = tile.getWidth();
        int height = tile.getHeight();

        if (width > WIDTH || width < WIDTH){
            tile.setX(tile.getX() - ((width - WIDTH) / 2));
        }
        if (height > HEIGHT || height < HEIGHT){
            tile.setY(tile.getY() - (height - HEIGHT));

        }
    }

    public ArrayList<Sprite> generateTileMap(Tile[] tiles){

        ArrayList<Sprite> tileMap = new ArrayList<>();

        for (Tile tile: tiles) {
            tileMap.add(tile);
        }

        return tileMap;
    }

    public int generateX(int a, int w){
        return (a % w) * WIDTH;
    }

    public int generateY(int a, int w){
        return (a / w) * HEIGHT;
    }

    public void generateTileTypes(Tile[] tiles, int[] typeMap){

        for (int i = 0; i < tiles.length; i++){
            configureTileTypes(tiles[i], typeMap[i]);
        }
    }

    public void configureTileTypes(Tile tile, int typeID){

        switch (typeID){

            case 100 : tile.setTileType("Nullish");
                break;
            case 101 : tile.setTileType("Floor");
                break;
            case 102 : tile.setTileType("Dangerous");
                break;
            case 103 : tile.setTileType("Wall");
                break;
            case 104 : tile.setTileType("Jumpable");
                break;
            case 105 : tile.setTileType("Changeable");
                tile.setMapChangeCode(mapChangeCodes.next());
                break;
            case 200 : tile.setTileType("");
                break;
            case 201 : tile.setTileType("Movable");
                break;
            case 202 : tile.setTileType("Vendor");
                break;
            case 203 : tile.setTileType("");
                break;
            case 204 : tile.setTileType("");
                break;
            case 205 : tile.setTileType("");
                break;
            default: System.out.println("Error: Could not determine Tile type");
        }
    }
    
    // loading Images

    private ArrayList<BufferedImage> allImages;

    public void loadUsedImages(int[] mapCodes, String sceneCode){
        allImages.clear();
        int maxCode = 0;

        for (int i = 0; i < mapCodes.length; i++){
            if (mapCodes[i] > maxCode){
                maxCode = mapCodes[i];
            }
        }

        for (int i = 0; i <= maxCode; i++){
            allImages.add(interpretTileCode(i, sceneCode));
        }
    }

    public BufferedImage interpretTileCode(int tileCode, String sceneCode){

        String s = "Tile_Sprites/" + sceneCode + (tileCode + 1) + ".png";
        return getImageForLoading(s);
    }

    public BufferedImage getImageForLoading(String s){
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            GraphicsConfiguration config = device.getDefaultConfiguration();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            
            URL url = classLoader.getResource(s);

            BufferedImage tempImage = ImageIO.read(url);

            //BufferedImage tempBuffImage = config.createCompatibleImage((int)(ImageIO.read(url).getWidth(null) * SCALE_X),
            //        (int)(ImageIO.read(url).getHeight(null) * SCALE_Y), Transparency.TRANSLUCENT);

            BufferedImage tempBuffImage = new BufferedImage((int)(ImageIO.read(url).getWidth(null) * SCALE_X),
                    (int)(ImageIO.read(url).getHeight(null) * SCALE_Y), BufferedImage.TYPE_INT_ARGB);

            Graphics2D tempGraphics = tempBuffImage.createGraphics();

            tempGraphics.scale(SCALE_X, SCALE_Y);
            tempGraphics.drawImage(tempImage, 0, 0, null);

            tempGraphics.dispose();

            return tempBuffImage;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error: No such Image. Change URL String.");
        }
        return null;
    }
}
