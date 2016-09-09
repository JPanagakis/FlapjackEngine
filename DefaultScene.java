/**
 * Created by justin on 9/9/16.
 */
public class DefaultScene extends MapScene {

    public DefaultScene(){
        super();

        setCurrentMap(new DefaultMap());

        initCamera();
    }
}
