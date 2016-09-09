import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by justin on 6/27/16.
 */
public class SaveManager {

    private static SaveManager saveManager = new SaveManager();
    private SaveManager(){}
    public static SaveManager getSaveManager(){return saveManager;}

    private Player player;

    public Player getPlayer(){return player;}

    public void startNewGame(String saveFile){

        player = new Player();

        // Last thing to do upon starting a new game
        saveGame(saveFile);
    }

    public void saveGame(String saveFile){
        try {
            // Write to disk with FileOutputStream
            FileOutputStream f_out = new FileOutputStream(saveFile + ".data");
            // Write object with ObjectOutputStream
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
            // Write object out to disk
            obj_out.writeObject(player);

            f_out.close();
            obj_out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadGame(String saveFile){
        try {
            //Read from disk using FileInputStream
            FileInputStream f_in = new FileInputStream(saveFile + ".data");
            //Read object using ObjectInputStream
            ObjectInputStream obj_in = new ObjectInputStream(f_in);
            //Read an object
            Object obj = obj_in.readObject();

            if (obj instanceof Player){
                //Cast object to a Player
                player = (Player) obj;
            }

            f_in.close();
            obj_in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
