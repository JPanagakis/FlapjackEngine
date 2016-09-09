import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by justin on 5/8/16.
 */
public class Scene {

    protected ArrayList<Sprite> floorSprites;
    protected ArrayList<Sprite> currentSprites;
    protected ArrayList<Rectangle> currentRectangles;
    protected ArrayList<GString> currentMessages;
    protected Image background;
    protected ClassLoader classLoader;
    protected MilliTimer timer;
    protected String exitCode;

    protected final double SCALE_X = OptionsManager.get().getScaleRatioX();
    protected final double SCALE_Y = OptionsManager.get().getScaleRatioY();

    //Save/Load
    protected SaveManager saveManager;
    protected Player player;

    public Player getPlayer(){return player;}

    //Debugging
    protected boolean debugMode;
    protected ArrayList<GString> debugMessages;
    GString debug1, debug2, debug3, debug4, debug5, debug6, debug7, debug8, debug9,
            debug10, debug11, debug12, debug13, debug14, debug15, debug16, debug17;

    //Collision

    
    //Scene Loading
    protected boolean loading = false;
    protected double loadOpacity = 0.0;
    protected int loadCycle = 1;
    protected boolean fadedIn = false;

    public Scene(){
        timer = new MilliTimer();
        floorSprites = new ArrayList<>();
        currentSprites = new ArrayList<>();
        currentMessages = new ArrayList<>();

        saveManager = SaveManager.getSaveManager();

        debugMode = false;

        debugMessages = new ArrayList<>();

        exitCode = "";
    }

    public void startDebugMode(){

        debugMode = true;

        debug1 = new GString("", 0, 20);
        debug2 = new GString("", 0, 40);
        debug3 = new GString("", 0, 60);
        debug4 = new GString("", 200, 20);
        debug5 = new GString("", 200, 40);
        debug6 = new GString("", 350, 20);
        debug7 = new GString("", 550, 20);
        debug8 = new GString("", 550, 40);
        debug9 = new GString("", 750, 20);
        debug10 = new GString("", 1000, 20);
        debug11 = new GString("", 1000, 40);
        debug12 = new GString("", 1000, 60);
        debug13 = new GString("", 1000, 80);
        debug14 = new GString("", 1000, 100);
        debug15 = new GString("", 1000, 120);
        debug16 = new GString("", 1000, 140);
        debug17 = new GString("", 1000, 160);

        debugMessages.add(debug1);
        debugMessages.add(debug2);
        debugMessages.add(debug3);
        debugMessages.add(debug4);
        debugMessages.add(debug5);
        debugMessages.add(debug6);
        debugMessages.add(debug7);
        debugMessages.add(debug8);
        debugMessages.add(debug9);
        debugMessages.add(debug10);
        debugMessages.add(debug11);
        debugMessages.add(debug12);
        debugMessages.add(debug13);
        debugMessages.add(debug14);
        debugMessages.add(debug15);
        debugMessages.add(debug16);
        debugMessages.add(debug17);
    }

    public ArrayList<Sprite> getFloorSprites(){ return floorSprites; }

    public ArrayList<Sprite> getSprites(){
        return currentSprites;
    }

    public ArrayList<Rectangle> getRectangles(){ return currentRectangles; }

    public ArrayList<GString> getMessages() { return currentMessages; }

    public Image getBackground(){
        return background;
    }

    public Image getLoadedBackgroundImage(String s){
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(s);
            ImageIcon ii = new ImageIcon(url);
            return ii.getImage();
        } catch (Exception e){
            System.out.println("Error: No such Image. Change URL String.");
        }
        return null;
    }

    public void updateScene(){

    }

    public String getExitCode(){
        return exitCode;
    }

    public void setExitCode(String ec) { exitCode = ec; }

    public void setFadedIn(boolean b){fadedIn = b;}

    public boolean isLoading(){return loading;}

    public double getLoadOpacity(){return loadOpacity;}

    public void setLoadOpacity(double loadOpacity){this.loadOpacity = loadOpacity;}

    public void forward(){

    }

    public void back(){

    }

    public void triangle(){

    }

    public void square(){

    }

    public void L1(){

    }

    public void L2(){

    }

    public void R1(){

    }

    public void R2(){

    }

    public void up(){

    }

    public void down(){

    }

    public void left(){

    }

    public void right(){

    }

    public void forwardReleased(){

    }

    public void backReleased(){

    }

    public void triangleReleased(){

    }

    public void squareReleased(){

    }

    public void L1Released(){

    }

    public void L2Released(){

    }

    public void R1Released(){

    }

    public void R2Released(){

    }

    public void upReleased(){

    }

    public void downReleased(){

    }

    public void leftReleased(){

    }

    public void rightReleased(){

    }

    //Lazy!
    public void log(String s){
        System.out.println(s);
    }
}
