import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by justin on 5/6/16.
 */
public class ScreenManager {

    private GraphicsDevice vc;
    private JFrame window;
    private Canvas canvas;
    private BufferStrategy strategy;
    private GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

    public ScreenManager(){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
    }

    //get all compatible display modes
    public DisplayMode[] getCompatibleDisplayModes(){
        return vc.getDisplayModes();
    }

    //compare DM passed in to Video Card DM
    public DisplayMode findFirstCompatibleMode(DisplayMode[] modes){
        DisplayMode[] goodModes = getCompatibleDisplayModes();
        for(int i = 0; i < modes.length; i++){
            for(int j = 0; j < goodModes.length; j++){
                if(displayModesMatch(modes[i], goodModes[j])){
                    System.out.println("Found compatible Display Mode!");
                    return modes[i];
                }
            }
        }
        System.out.println("Error: Could not find compatible Display Mode");
        return null;
    }

    public DisplayMode getCurrentDisplayMode(){
        return vc.getDisplayMode();
    }

    //check if two modes match each other
    public boolean displayModesMatch(DisplayMode m1, DisplayMode m2){
        if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()){
            return false;
        }
        if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
                && m1.getBitDepth() != m2.getBitDepth()){
            return false;
        }
        if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && m1.getRefreshRate() != m2.getRefreshRate()){
            return false;
        }
        System.out.println("Display Modes Match!");
        return true;
    }

    public void setFullScreen(DisplayMode dm){

        window = new JFrame();

        window.setUndecorated(true);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setIgnoreRepaint(true);
        vc.setFullScreenWindow(window);

        if (dm != null && vc.isDisplayChangeSupported()){
            try{
                vc.setDisplayMode(dm);
            }catch(Exception e){
                System.out.println("Error: Could not change display mode");
                e.printStackTrace();
            }
        }
        //window.createBufferStrategy(2);
        //vc.getFullScreenWindow().createBufferStrategy(2);

        canvas = new Canvas(config);
        canvas.setSize(getWidth(), getHeight());
        window.add(canvas, 0);
        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
    }

    public Canvas getCanvas(){return canvas;}

    //set Graphics object = to the following
    public Graphics2D getGraphics(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            BufferStrategy bs = w.getBufferStrategy();
            //return (Graphics2D)bs.getDrawGraphics();
            return (Graphics2D)strategy.getDrawGraphics();
        } else {
            return null;
        }
    }

    //updates display
    public void update(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            BufferStrategy bs = w.getBufferStrategy();

            if(!strategy.contentsLost()){
                //bs.show();
                strategy.show();
                Toolkit.getDefaultToolkit().sync();
            }
        }
    }

    //returns full screen window
    public Window getFullScreenWindow(){
        return vc.getFullScreenWindow();
    }

    //get width of window
    public int getWidth(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            return w.getWidth();
        } else {
            return 0;
        }
    }

    //get height of window
    public int getHeight(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            return w.getHeight();
        } else {
            return 0;
        }
    }

    //get out of fullscreen
    public void restoreScreen(){

        Window w = vc.getFullScreenWindow();
        if (w != null){
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }

    //create image compatible with monitor
    public BufferedImage createCompatibleImage(int width, int height, int transparency){
        Window w = vc.getFullScreenWindow();
        if(w != null){

            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            GraphicsConfiguration config = device.getDefaultConfiguration();

            //GraphicsConfiguration gc = w.getGraphicsConfiguration();
            return config.createCompatibleImage(width, height, transparency);
        } else {
            return null;
        }
    }

    public void minimize(){
        window.setState(Frame.ICONIFIED);
    }
}
