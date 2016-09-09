/**
 * Created by justin on 5/20/16.
 */
public class OptionsManager {

    private static OptionsManager optionsManager = new OptionsManager();
    public static OptionsManager get(){return optionsManager;}

    //Scaling
    private double scaleRatioX;
    private double scaleRatioY;
    private double viewportX, viewportY;

    public void setScaleRatioX(double srX){
        scaleRatioX = srX;
    }

    public void setScaleRatioY(double srY){
        scaleRatioY = srY;
    }

    public double getScaleRatioX(){
        return scaleRatioX;
    }

    public double getScaleRatioY(){
        return scaleRatioY;
    }

    public void setViewportX(double vpX){viewportX = vpX;}

    public void setViewportY(double vpY){viewportY = vpY;}

    public double getViewportX(){return viewportX;}

    public double getViewportY(){return viewportY;}

    //Saving
    private String saveSlot;

    public void setSaveSlot(String slot){
        saveSlot = slot;
    }

    public String getSaveSlot(){
        return saveSlot;
    }
}
