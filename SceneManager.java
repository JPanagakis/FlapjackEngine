import java.util.Stack;

/**
 * Created by justin on 5/8/16.
 */
public class SceneManager {

    private boolean running;
    private Scene currentScene;
    private MilliTimer timer;

    private boolean loading = false;
    private double loadOpacity = 0.0;
    private int loadCycle = 1;
    private boolean fadedIn = false;

    private Stack<Scene> sceneStack;

    public SceneManager(){

        sceneStack = new Stack<>();

        running = true;
        timer = new MilliTimer();

        sceneStack.push(new DefaultScene());

        setCurrentScene();
    }

    public void update(){
        setCurrentScene();

        currentScene.updateScene();

        switch (loadCycle) {
            case 1:
                if (!currentScene.getExitCode().equals("")) {

                    Thread fadeInThread = new Thread(new Runnable() {

                        SceneManager sm = getSceneManager();

                        @Override
                        public void run() {
                            MilliTimer fTimer = new MilliTimer();
                            int runTime = 300;
                            fTimer.start();
                            while (fTimer.getElapsedTime() < runTime){
                                sm.setLoadOpacity((fTimer.getElapsedTime() / (double)runTime));
                            }
                            setFadedIn(true);
                        }
                    });

                    loading = true;
                    try {
                        fadeInThread.start();
                    } catch (IllegalThreadStateException e){
                        e.printStackTrace();
                        fadeInThread.run();
                    }

                    loadCycle = 2;
                }
                break;
            case 2:
                if (fadedIn) {
                    String code = currentScene.getExitCode();
                    currentScene.setExitCode("");

                    changeScene(code);
                    setCurrentScene();

                    currentScene.setExitCode("");

                    loadCycle = 3;
                }
                break;
            case 3:

                Thread fadeoutThread = new Thread(new Runnable() {

                    SceneManager sm = getSceneManager();

                    @Override
                    public void run() {
                        MilliTimer fTimer = new MilliTimer();
                        int runTime = 300;
                        fTimer.start();
                        while (fTimer.getElapsedTime() < runTime){
                            sm.setLoadOpacity(1 - (fTimer.getElapsedTime() / (double)runTime));
                        }
                        setFadedIn(false);
                        loading = false;
                    }
                });

                try {
                    fadeoutThread.start();
                } catch (IllegalThreadStateException e){
                    e.printStackTrace();
                    fadeoutThread.run();
                }

                loadCycle = 1;
                break;
        }
    }

    //temp
    public SceneManager getSceneManager(){return this;}
    public void setFadedIn(boolean b){fadedIn = b;}

    public void setCurrentScene(){
        currentScene = sceneStack.get(sceneStack.size() - 1);
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    public boolean isLoading(){return loading;}

    public double getLoadOpacity(){return loadOpacity;}

    public void setLoadOpacity(double loadOpacity){this.loadOpacity = loadOpacity;}

    public void changeScene(String exitCode){

        switch (exitCode){

            case "pop":
                sceneStack.pop();
                break;

            default:
                sceneStack.push(new DefaultScene());
        }
    }
}
