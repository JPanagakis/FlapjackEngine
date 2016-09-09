/**
 * Created by justin on 5/9/16.
 */
public class MilliTimer {

    long startTime;
    long currentTime;

    public MilliTimer(){
        start();
    }

    public void start(){
        startTime = System.currentTimeMillis();
    }

    public long getElapsedTime(){
        currentTime = System.currentTimeMillis();
        return currentTime - startTime;
    }
}
