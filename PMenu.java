import java.util.ArrayList;

/**
 * Created by justin on 6/28/16.
 */
public class PMenu {

    protected ArrayList<PMenuOption> options;
    protected ArrayList<GString> generalMessages;
    protected int optionMarker;
    protected PMenuOption selectedOption;
    protected String name;
    protected double xStandard;
    protected double yStandard;

    public PMenu(){
        options = new ArrayList<>();
        optionMarker = 0;
    }

    public double getxStandard(){return xStandard;}

    public double getyStandard(){return yStandard;}

    public void increaseOptionMarker(){
        if (optionMarker == options.size() - 1){
            optionMarker = 0;
        } else {
            optionMarker++;
        }
        setSelectedOption();
    }

    public void decreaseOptionMarker(){
        if (optionMarker == 0){
            optionMarker = options.size() - 1;
        } else {
            optionMarker--;
        }
        setSelectedOption();
    }

    public void setSelectedOption(){
        if (options.size() > 0) {
            selectedOption = options.get(optionMarker);
        }
    }

    public int getOptionMarker(){return optionMarker;}

    public String getOptionName(int marker){
        try {
            return options.get(marker).getName();
        } catch (Exception e){
            return "";
        }

    }

    public PMenuOption getOption(int marker){
        try {
            return options.get(marker);
        } catch (Exception e){
            return null;
        }
    }

    public ArrayList<PMenuOption> getOptions(){return options;}

    public ArrayList<GString> getGeneralMessages(){return generalMessages;}

    public PMenuOption getSelectedOption(){
        return selectedOption;
    }

    public String getName(){
        return name;
    }

    public void generateOptions(ArrayList<PMenuOption> list){

        for (int i = 0; i < list.size(); i++){
            options.add(list.get(i));
        }
    }
}
