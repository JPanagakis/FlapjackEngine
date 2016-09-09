import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by justin on 5/8/16.
 */
public class Sprite {

    private final double SCALE_X = OptionsManager.get().getScaleRatioX();
    private final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    protected float x;
    protected float y;
    protected float dx;
    protected float dy;

    protected BufferedImage image;
    protected Animation animation;
    protected transient ClassLoader classLoader;
    protected ArrayList<BufferedImage> images;

    protected GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    protected GraphicsDevice device = env.getDefaultScreenDevice();
    protected GraphicsConfiguration config = device.getDefaultConfiguration();
    protected int imageWidth;
    protected int imageHeight;
    protected double localScale = 1.0;


    public Sprite (boolean loadImages){

        if (loadImages) {
            loadImages();
        }
    }

    public Sprite (float x, float y, boolean loadImages){
        this.x = x;
        this.y = y;
        if (loadImages) {
            loadImages();
        }
    }

    public Sprite (float x, float y, Animation a, boolean loadImages){
        this.x = x;
        this.y = y;
        this.animation = a;
        if (loadImages) {
            loadImages();
        }
    }

    public Sprite (float x, float y, float vx, float vy, boolean loadImages){
        this.x = x;
        this.y = y;
        this.dx = vx;
        this.dy = vy;
        if (loadImages) {
            loadImages();
        }
    }

    public Sprite (float x, float y, float vx, float vy, Animation a, boolean loadImages){
        this.x = x;
        this.y = y;
        this.dx = vx;
        this.dy = vy;
        this.animation = a;
        if (loadImages) {
            loadImages();
        }
    }

    public void updateMovement(long timePassed){

        x += dx * timePassed;
        y += dy * timePassed;
    }

    public void updateAnimation(long timePassed){
        if (animation != null) {
            animation.update(timePassed);
        }
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public int getWidth(){
        return imageWidth;
    }

    public void setWidth(int imageWidth){
        this.imageWidth = imageWidth;
    }

    public int getHeight(){
        return imageHeight;
    }

    public void setHeight(int imageHeight){
        this.imageHeight = imageHeight;
    }

    public float getVelocityX(){
        return dx;
    }

    public float getVelocityY(){
        return dy;
    }

    public void setVelocityX(float vx){
        this.dx = vx;
    }

    public void setVelocityY(float vy){
        this.dy = vy;
    }

    public BufferedImage getImage(){
        if (animation != null) {
            return animation.getImage();
        } else if (image != null){
            return getStaticImage();
        } else {
            return null;
        }
    }

    public BufferedImage getStaticImage(){ return image; }

    public void loadImages(){
        classLoader = Thread.currentThread().getContextClassLoader();

    }

    public void loadImage(String fileFolder, String fileName){
        String s = fileFolder + "/" + fileName + ".png";
        image = getImageForLoading(s);
    }

    public void scale(int scaleX, int scaleY){
        image = scale2(image, scaleX, scaleY);
    }

    public BufferedImage scale2(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            //scaledImage = new BufferedImage(dWidth, dHeight, Transparency.BITMASK);
            scaledImage = config.createCompatibleImage(dWidth, dHeight, Transparency.TRANSLUCENT);

            Graphics2D graphics2D = (Graphics2D)scaledImage.getGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }

    protected float opacity = (float)1.0;

    public void setOpacity(float opacity){
        this.opacity = opacity;
    }

    public float getOpacity(){return opacity;}

    public void setLocalScale(double ls){
        localScale = ls;
    }

    public double getLocalScale(){return localScale;}

    public BufferedImage getImageForLoading(String s){
        try {
            URL url = classLoader.getResource(s);

            BufferedImage tempImage = ImageIO.read(url);

            setWidth((int)(ImageIO.read(url).getWidth(null) * SCALE_X));
            setHeight((int)(ImageIO.read(url).getHeight(null) * SCALE_Y));

            //BufferedImage tempBuffImage = config.createCompatibleImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
            BufferedImage tempBuffImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D tempGraphics = tempBuffImage.createGraphics();

            tempGraphics.scale(SCALE_X, SCALE_Y);
            tempGraphics.drawImage(tempImage, 0, 0, null);

            tempGraphics.dispose();

            return tempBuffImage;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Error: No such Image. Change URL String.");
        }
        return null;
    }

    ///////////////////////   For MapScenes  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    protected int cLeft, cRight, cUp, cDown;
    private int locX, locY;
    protected Tile currentTile, leftTile, rightTile, upTile, downTile;
    private int arrayLoc;
    private String tileChangeCode;
    private int layerNum;

    public int getLayerNum(){return layerNum;}

    public void setLayerNum(int layer){layerNum = layer;}

    public void updateCollisionBounds(){
    }

    public int getcLeft(){
        return cLeft;
    }

    public int getcRight(){
        return cRight;
    }

    public int getcUp(){
        return cUp;
    }

    public int getcDown(){
        return cDown;
    }

    public void updateLocation(){
        //this.locX = (int)(x + 25 * SCALE_X);
        //this.locY = (int)(y + 75 * SCALE_Y);
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

    public void setCurrentTile(Tile tile){
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
    }

    public Tile getDoubleLeftTile(){ return leftTile.getLeftTile(); }

    public Tile getDoubleRightTile(){ return rightTile.getRightTile(); }

    public Tile getDoubleUpTile(){ return upTile.getUpTile(); }

    public Tile getDoubleDownTile(){ return downTile.getDownTile(); }

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

    public void setAnimation(String direction){
        //// TODO: 5/29/16
    }

    /////////////////////  Fight Scenes \\\\\\\\\\\\\\\\\\\\\

    protected int sidePriority;

    protected float xStart, xGoal;
    protected float yStart, yGoal;

    protected boolean enemyTerratory;

    public void setSidePriority(int priority){sidePriority = priority;}

    public int getSidePriority(){return sidePriority;}

    public void setupGoals(){

    }

    public void setxGoal(float goal){
        xStart = x;
        xGoal = goal;
    }

    public float getxGoal(){return xGoal;}

    public void setyGoal(float goal){
        yStart = y;
        yGoal = goal;
    }

    public float getyGoal(){return yGoal;}

    public boolean isInEnemyTerratory(){return enemyTerratory;}

    public void setEnemyTerratory(boolean b){enemyTerratory = b;}


    /////////////////////   Cut Scenes  \\\\\\\\\\\\\\\\\\\\\

    protected float cutEndX, cutEndY;
    protected String cutType;
    protected boolean cutFin;

    public void setCutEndX(float cex){
        cutEndX = cex;
    }

    public float getCutEndX(){
        return cutEndX;
    }

    public void setCutEndY(float cey){
        cutEndY = cey;
    }

    public float getCutEndY(){
        return  cutEndY;
    }

    public void setCutType(String s){
        cutType = s;
    }

    public String getCutType(){
        return cutType;
    }

    public void setCutFin(boolean b){
        cutFin = b;
    }

    public boolean getCutFin(){
        return cutFin;
    }
}
