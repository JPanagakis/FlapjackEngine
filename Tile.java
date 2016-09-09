import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by justin on 5/15/16.
 */
public class Tile extends Sprite{

    private final double SCALE_X = OptionsManager.get().getScaleRatioX();
    private final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    private String sceneCode;

    private boolean dangerous;
    private String mapChangeCode;
    private String tileType;

    //Collsion
    private int locX, locY;
    //private Sprite currentTile, leftTile, rightTile, upTile, downTile;
    private int arrayLoc;
    private String tileChangeCode;

    //public Tile(int tileCode, String sceneCode, int x, int y){
    public Tile(BufferedImage image, int x, int y){
        super(x, y, false);

        this.image = image;
        //this.sceneCode = sceneCode;
        //loadImages();
        //interpretTileCode(tileCode);
        updateLocation();
        updateCollisionBounds();
    }

    public void setDangerous(boolean b){
        dangerous = b;
    }

    public boolean isDangerous(){
        return dangerous;
    }

    public void setTileType(String s){
        tileType = s;
    }

    public String getTileType(){
        return tileType;
    }

    public void setTileCode(int tileCode){
        interpretTileCode(tileCode);
    }

    public void interpretTileCode(int tileCode){

        String s = "Tile_Sprites/" + sceneCode + (tileCode + 1) + ".png";
        image = getImageForLoading(s);
    }

    @Override
    public void updateCollisionBounds(){
        //cUp = (int)(y + (getHeight() - (50 * SCALE_Y)));
        //cDown = (int)(y + getHeight());
        //cLeft = (int)(x);
        //cRight = (int)(x + getWidth());

        cUp = (int)(locY - 25 * SCALE_Y);
        cDown = (int)(locY + 25 * SCALE_Y);
        cLeft = (int)(locX - 25 * SCALE_X);
        cRight = (int)(locX + 25 * SCALE_X);
    }

    @Override
    public void updateLocation(){
        //this.locX = (int)(x + (getWidth() - (25 * SCALE_X)));
        //this.locY = (int)(y + (getHeight() - (25 * SCALE_Y)));
        this.locX = (int)(x + (getWidth() / 2));
        this.locY = (int)(y + getHeight() - (25 * SCALE_Y));
    }

    public void setArrayLocation(int loc){
        this.arrayLoc = loc;
    }

    public int getArrayLoc(){
        return arrayLoc;
    }

    public int getLocX(){
        return locX;
    }

    public int getLocY(){
        return locY;
    }

    /*public void setCurrentTile(Sprite tile){
        this.currentTile = tile;
    }

    public void setLeftTile(Tile tile){
        this.leftTile = tile;
    }

    public void setRightTile(Tile tile){
        this.rightTile = tile;
    }

    public void setUpTile(Tile tile){
        this.upTile = tile;
    }

    public void setDownTile(Tile tile){
        this.downTile = tile;
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public Tile getLeftTile(){
        return leftTile;
    }

    public Tile getRightTile(){
        return rightTile;
    }

    public Tile getUpTile(){
        return upTile;
    }

    public Tile getDownTile(){
        return downTile;
    }*/

    @Override
    public boolean checkTileChange(){
        if (leftTile != null){
            if (locX < leftTile.getcRight()){
                tileChangeCode = "Left";
                return true;
            }
        }
        if (rightTile != null){
            if (locX > rightTile.getcLeft()){
                tileChangeCode = "Right";
                return true;
            }
        }
        if (upTile != null){
            if (locY < upTile.getcDown()){
                tileChangeCode = "Up";
                return true;
            }
        }
        if (downTile != null){
            if (locY > downTile.getcUp()){
                tileChangeCode = "Down";
                return true;
            }
        }
        return false;
    }

    public String getTileChangeCode(){
        return tileChangeCode;
    }

    @Override
    public void updateAnimation(long timePassed){
        super.updateAnimation(timePassed);

        updateMovement(timePassed);
    }

    public void setMapChangeCode(String s) {
        this.mapChangeCode = s;
    }

    public String getMapChangeCode(){
        return mapChangeCode;
    }
}
