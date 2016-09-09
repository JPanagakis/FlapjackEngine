import java.util.concurrent.TimeUnit;

/**
 * Created by justin on 5/1/16.
 */
public class FPSTimeGenerator {

    private int TARGET_FPS;
    private long OPTIMAL_TIME;
    private long lastLoopTime, now, updateLength;
    private double delta;

    public FPSTimeGenerator(int fps){

        TARGET_FPS = fps;
        OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        start();
    }

    public void start(){

        lastLoopTime = System.nanoTime();
    }

    public void update(){

        now = System.nanoTime();
        updateLength = now - lastLoopTime;
        lastLoopTime = now;
        delta = updateLength / ((double)OPTIMAL_TIME);
    }

    public void delay(){

        try{
            Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME)/1000000 );
            //Thread.sleep(17);
        } catch (InterruptedException ie){

            System.out.println("Delay didn't work");
        }
    }

    public long getTimePassed(){

        return TimeUnit.MILLISECONDS.convert(updateLength, TimeUnit.NANOSECONDS);
    }

    public double getFPS(){
        return (1 / delta) * TARGET_FPS;
    }
}
