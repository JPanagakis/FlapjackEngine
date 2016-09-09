import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by justin on 5/6/16.
 */
public class Animation {

    private ArrayList frames;
    private int frameIndex;
    private long movieTime, totalTime;

    //Constructor
    public Animation(){

        frames = new ArrayList();
        totalTime = 0;
        start();
    }

    //add scene to ArrayList and set time for each scene
    public synchronized void addFrame(BufferedImage i, long t){
        totalTime += t;
        frames.add(new Frame(i, totalTime));
    }

    //start animation from beginning
    public synchronized void start(){
        movieTime = 0;
        frameIndex = 0;
    }

    //change scenes
    public synchronized void update(long timePassed){
        if(frames.size() > 1){
            movieTime += timePassed;
            if (movieTime >= totalTime){
                movieTime = 0;
                frameIndex = 0;
            }
            while (movieTime > getFrame(frameIndex).endTime){
                frameIndex++;
            }
        }
    }

    //get animation's current scene
    public synchronized BufferedImage getImage(){
        if (frames.size() == 0){
            return null;
        } else {
            return getFrame(frameIndex).pic;
        }
    }

    //get Scene
    private Frame getFrame(int x){
        return (Frame)frames.get(x);
    }

    ///////// PRIVATE INNER CLASS ///////////

    private class Frame{

        BufferedImage pic;
        long endTime;

        public Frame(BufferedImage picture, long endTime){
            this.pic = picture;
            this.endTime = endTime;
        }
    }
}
