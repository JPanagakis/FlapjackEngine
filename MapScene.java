import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by justin on 5/14/16.
 */
public class MapScene extends Scene{

    private final double SCALE_X = OptionsManager.get().getScaleRatioX();
    private final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    protected Sprite playerSprite;
    protected ArrayList<Sprite> floorTileMap;
    protected ArrayList<Sprite> midTileMap;
    protected ArrayList<Sprite> highTileMap;
    protected MapGenerator mapGenerator;
    protected int tileWidth = 0;

    public MapScene(){
        super();

        currentSprites = new ArrayList<>();
        currentMessages = new ArrayList<>();
        midTileMap = new ArrayList<>();
        highTileMap = new ArrayList<>();
        mapGenerator = new MapGenerator();
        
        // Visual Sprites
        setupLayers();
        things = new ArrayList<>();
        mergedVisualSprites = new ArrayList<>();

        playerSprite = new Sprite(false);
        things.add(playerSprite);

        actors = new ArrayList<>();
    }

    public void updateDebugMode(){

        try {
            debug1.setString("");
            debug2.setString("");
            debug3.setString("");
            debug4.setString("");
            debug5.setString("");
            debug6.setString("");
            debug7.setString("");
            debug8.setString("");
            debug10.setString("");
            debug11.setString("");
            debug10.setString("");
            debug11.setString("");
            debug12.setString("");
            debug13.setString("");
            debug12.setString("");
            debug13.setString("");
            debug14.setString("");
            debug15.setString("");
            debug14.setString("");
            debug15.setString("");
            debug16.setString("");
            debug17.setString("");
            debug16.setString("");
            debug17.setString("");
        } catch (Exception e){

        }

    }

    public void setCurrentMap(Map map){

        layers = map.getLayers();

        tileWidth = map.getTileWidth();
    }

    //////////////////////////   Collision   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public void updatePlayerSprite(){

    }
    
    ///////////////////        Logic Layer          \\\\\\\\\\\\\\\\\\\

    public void updateAllSprites(){
        for (int i = 0; i < layers.length; i++){
            for (int j = 0; j < layers[i].size(); j++){
                layers[i].get(j).updateLocation();
                layers[i].get(j).updateCollisionBounds();
                layers[i].get(j).setArrayLocation(j);
                updateTileCollision(i, j, layers[i].get(j));
            }
        }
        for (int i = 0; i < things.size(); i++){
            things.get(i).updateLocation();
            things.get(i).updateCollisionBounds();
            updateThingCollision(things.get(i).getLayerNum(), things.get(i));
        }
    }

    public void updateTileCollision(int layer, int arrayLoc, Sprite sprite){

        if (arrayLoc - 1 >= 0) {
            sprite.setLeftTile((Tile) layers[layer].get(arrayLoc - 1));
        } else {sprite.setLeftTile(null);}
        if (arrayLoc + 1 < layers[layer].size()) {
            sprite.setRightTile((Tile) layers[layer].get(arrayLoc + 1));
        } else {sprite.setRightTile(null);}
        if (arrayLoc - tileWidth >= 0) {
            sprite.setUpTile((Tile) layers[layer].get(arrayLoc - tileWidth));
        } else {sprite.setUpTile(null);}
        if (arrayLoc + tileWidth < layers[layer].size()) {
            sprite.setDownTile((Tile) layers[layer].get(arrayLoc + tileWidth));
        } else {sprite.setDownTile(null);}
    }

    public void updateThingCollision(int layer, Sprite sprite){
        int xloc = sprite.getLocX();
        int yloc = sprite.getLocY();
        for (int i = 0; i < layers[layer].size(); i++){
            if (xloc > layers[layer].get(i).getcLeft() && xloc <= layers[layer].get(i).getcRight() &&
                    yloc > layers[layer].get(i).getcUp() && yloc <= layers[layer].get(i).getcDown()){
                sprite.setArrayLocation(i);
            }
        }
        sprite.setCurrentTile((Tile)layers[layer].get(sprite.getArrayLoc()));
        updateTileCollision(layer, sprite.getArrayLoc(), sprite);
    }
    
    
    ///////////////////       Visual Layer          \\\\\\\\\\\\\\\\\\\
    
    protected ArrayList<Sprite> mergedVisualSprites;

    protected ArrayList<Sprite>[] layers = new ArrayList[10];

    protected ArrayList<Sprite> things;

    public void setupLayers(){
        for (int i = 0; i < layers.length; i++){
            layers[i] = new ArrayList<>();
        }
    }

    public void mergeVisualLayer(){
        mergedVisualSprites.clear();
        
        for (int i = 0; i < layers.length; i++){
            ArrayList<Sprite> tempMerge = new ArrayList<>();
            for (int h = 0; h < things.size(); h++){
                if (things.get(h).getLayerNum() == i){
                    tempMerge.add(things.get(h));
                }
            }
            for (int j = 0; j < layers[i].size(); j++){
                tempMerge.add(layers[i].get(j));
            }
            sortSpriteArrayList(tempMerge);
            for (int k = 0; k < tempMerge.size(); k++){
                mergedVisualSprites.add(tempMerge.get(k));
            }
        }
    }

    //sort sprites by Y location
    public void sortSpriteArrayList(ArrayList<Sprite> sprites){
        for (int i = 0; i < sprites.size() - 1; i++){
            int smallest = i;
            for (int j = i + 1; j < sprites.size(); j++){
                if (sprites.get(j).getLocY() < sprites.get(smallest).getLocY()){
                    smallest = j;
                }
            }
            Collections.swap(sprites, i, smallest);
        }
    }
    
    

    @Override
    public synchronized void updateScene(){

        if (debugMode) {
            updateDebugMode();
            currentMessages = debugMessages;
        }

        updatePlayerSprite();

        updateAllSprites();
        mergeVisualLayer();
        currentSprites = mergedVisualSprites;

        updateMapChange();

        updateCamera();
    }


    //////////////////////////    Map Change   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    String mapChangeCode;

    public void updateMapChange(){

        switch(loadCycle) {

            case 1:
                if (playerSprite.getCurrentTile().getTileType().equals("Changeable")) {
                    Thread fadeInThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            MilliTimer fTimer = new MilliTimer();
                            int runTime = 300;
                            fTimer.start();
                            while (fTimer.getElapsedTime() < runTime){
                                setLoadOpacity((fTimer.getElapsedTime() / (double)runTime));
                            }
                            setFadedIn(true);
                        }
                    });

                    loading = true;
                    try {
                        fadeInThread.start();
                    } catch (IllegalThreadStateException e){
                        e.printStackTrace();
                        fadeInThread.run();
                    }
                    
                    loadCycle = 2;
                }
                break;
            case 2:
                if (fadedIn) {

                    doMapChange(mapChangeCode);

                    loadCycle = 3;
                }
                break;
            case 3:
                Thread fadeoutThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        MilliTimer fTimer = new MilliTimer();
                        int runTime = 300;
                        fTimer.start();
                        while (fTimer.getElapsedTime() < runTime){
                            setLoadOpacity(1 - (fTimer.getElapsedTime() / (double)runTime));
                        }
                        setFadedIn(false);
                        loading = false;
                    }
                });

                try {
                    fadeoutThread.start();
                } catch (IllegalThreadStateException e){
                    e.printStackTrace();
                    fadeoutThread.run();
                }

                loadCycle = 1;
                break;
        }
    }

    //Implement in Every MapScene
    public void doMapChange(String s){

    }

    public void loadVendor(){

    }

    //////////////////////////      Camera      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    double camX, camY;
    int viewportSizeX, viewportSizeY, worldSizeX, worldSizeY, offsetMinX, offsetMinY, offsetMaxX, offsetMaxY;

    public void initCamera() {
        viewportSizeX = (int) (OptionsManager.get().getViewportX() * SCALE_X);
        viewportSizeY = (int) (OptionsManager.get().getViewportY() * SCALE_Y);
        worldSizeX = (int) (tileWidth * 50 * SCALE_X);
        //worldSizeY = (int) ((floorTileMap.size() / tileWidth) * 50 * SCALE_Y);
        worldSizeY = (int) ((layers[0].size() / tileWidth) * 50 * SCALE_Y);
        offsetMinX = 0;
        offsetMinY = 0;
        offsetMaxX = worldSizeX - viewportSizeX;
        offsetMaxY = worldSizeY - viewportSizeY;
    }

    public void updateCamera(){

        if (viewportSizeX < worldSizeX) {
            camX = playerSprite.getLocX() - viewportSizeX / 2;
            if (camX > offsetMaxX){
                camX = offsetMaxX;
            } else if (camX < offsetMinX){
                camX = offsetMinX;
            }
        } else {
            camX = - (viewportSizeX - worldSizeX) / 2;
        }
        if (viewportSizeY < worldSizeY) {
            camY = playerSprite.getLocY() - viewportSizeY / 2;
            if (camY > offsetMaxY){
                camY = offsetMaxY;
            } else if (camY < offsetMinY){
                camY = offsetMinY;
            }
        } else {
            camY = - (viewportSizeY - worldSizeY) / 2;
        }
    }

    public double getCamX(){
        return camX;
    }

    public double getCamY(){
        return camY;
    }


    //////////////////////////     Controls     \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    private boolean fastMode = false;
    private boolean forwardPressed = false;
    private boolean backPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public void forward(){
        forwardPressed = true;

        if (!isCutSceneMode()){
            
        }
    }

    @Override
    public void back(){
        backPressed = true;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void triangle(){

    }

    @Override
    public void square(){

    }

    @Override
    public void up(){
        upPressed = true;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void down(){
        downPressed = true;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void left(){
        leftPressed = true;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void right(){
        rightPressed = true;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void forwardReleased(){
        forwardPressed = false;

        if (!isCutSceneMode()){
            
        }
    }

    @Override
    public void backReleased(){
        backPressed = false;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void triangleReleased(){
        
    }

    @Override
    public void squareReleased(){
        
    }

    @Override
    public void upReleased(){
        upPressed = false;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void downReleased(){
        downPressed = false;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void leftReleased(){
        leftPressed = false;

        if (!isCutSceneMode()) {
            
        }
    }

    @Override
    public void rightReleased(){
        rightPressed = false;

        if (!isCutSceneMode()) {
            
        }
    }

    ///////////////////  Collision Type Checks  \\\\\\\\\\\\\\\\\\\

    

    ///////////////////// General Cut Scenes \\\\\\\\\\\\\\\\\\\\\\

    protected boolean cutSceneMode = false;
    protected ArrayList<Sprite> actors;
    
    public boolean isCutSceneMode(){
        return cutSceneMode;
    }

    public void swapTiles(Sprite s1, Sprite s2){

        Collections.swap(layers[1], s1.getArrayLoc(), s2.getArrayLoc());
    }
}
