import java.awt.*;
import java.util.ArrayList;

/**
 * Created by justin on 5/8/16.
 */
public abstract class Core {

    //Scaling
    protected static double scaleRatioX;
    protected static double scaleRatioY;
    protected double screenPixelWidth;
    protected double screenPixelHeight;

    private static DisplayMode[] modes = {
            new DisplayMode(1920, 1080, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 720, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1366, 768, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1600, 900, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 800, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(800, 600, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
    };

    private boolean running;
    protected ScreenManager screenManager;
    protected FPSTimeGenerator timer;
    protected SceneManager sceneManager;
    protected Scene currentScene;
    protected ArrayList<Sprite> sprites;
    protected ArrayList<Rectangle> rectangles;
    protected ArrayList<GString> messages;

    //stop method
    public void stop(){
        sceneManager.setRunning(false);
        running = false;
    }

    //call init and gameloop
    public void run(){
        try{
            init();
            gameLoop();
        } finally {
            screenManager.restoreScreen();
        }
    }

    public void init(){
        screenManager = new ScreenManager();
        DisplayMode dm = screenManager.findFirstCompatibleMode(modes);
        System.out.println("Width: " + dm.getWidth());
        System.out.println("Height: " + dm.getHeight());
        System.out.println("Bit Depth: " + dm.getBitDepth());
        System.out.println("Refresh Rate: " + dm.getRefreshRate());
        screenManager.setFullScreen(dm);

        Window w = screenManager.getFullScreenWindow();
        w.setFont(new Font("Arial", Font.PLAIN, 12));
        w.setBackground(Color.BLACK);
        w.setForeground(Color.WHITE);

        setupScaling();

        timer = new FPSTimeGenerator(90);
        sceneManager = new SceneManager();

        running = true;
    }

    //main gameLoop
    public void gameLoop(){

        while (running){

            update(timer.getTimePassed());

            Graphics2D g = screenManager.getGraphics();
            draw(g);
            g.dispose();

            screenManager.update();

            timer.update();
            timer.delay();
        }
    }

    //update animation
    public void update(long timePassed){
    }

    //draws to screen
    public abstract void draw(Graphics2D g);

    //sets up screen scaling
    public void setupScaling(){
    }
}
