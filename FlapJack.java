import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by justin on 5/8/16.
 */
public class FlapJack extends Core implements KeyListener{

    public static void main(String[] args){
        new FlapJack().run();
    }

    private ArrayList<Sprite> floorSprites;
    private OptionsManager optionsManager = OptionsManager.get();

    public void init(){
        super.init();

        Window w = screenManager.getFullScreenWindow();
        w.setFocusTraversalKeysEnabled(false);
        w.addKeyListener(this);
        screenManager.getCanvas().addKeyListener(this);
    }

    @Override
    public void setupScaling(){
        //Scaling

        Window w = screenManager.getFullScreenWindow();
        screenPixelWidth = 1366;
        screenPixelHeight = 768;

        scaleRatioX = w.getWidth() / screenPixelWidth;
        scaleRatioY = w.getHeight() / screenPixelHeight;

        System.out.println("Scale Ratios: " + w.getWidth() / screenPixelWidth + " " + w.getHeight() / screenPixelHeight);

        if (scaleRatioX < scaleRatioY){
            scaleRatioY = scaleRatioX;
        } else {
            scaleRatioX = scaleRatioY;
        }

        optionsManager.setScaleRatioX(scaleRatioX);
        optionsManager.setScaleRatioY(scaleRatioY);

        optionsManager.setViewportX(screenPixelWidth);
        optionsManager.setViewportY(screenPixelHeight);
    }

    public void update(long timePassed){

        currentScene = sceneManager.getCurrentScene();
        floorSprites = currentScene.getFloorSprites();
        sprites = currentScene.getSprites();
        rectangles = currentScene.getRectangles();
        messages = currentScene.getMessages();

        checkKeys();

        if (floorSprites.size() > 0){
            for (int i = 0; i < floorSprites.size(); i++) {
                floorSprites.get(i).updateAnimation(timePassed);
            }
        }

        if (sprites.size() > 0) {
            for (int i = 0; i < sprites.size(); i++) {
                sprites.get(i).updateAnimation(timePassed);
            }
        }

        sceneManager.update();
    }

    public synchronized void draw(Graphics2D g){
        Window w = screenManager.getFullScreenWindow();

        g.setColor(w.getBackground());
        g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());

        if(currentScene instanceof MapScene) {
            g.translate(- ((MapScene) currentScene).getCamX(), - ((MapScene) currentScene).getCamY());
        }

        if (currentScene.getBackground() != null){
            g.drawImage(currentScene.getBackground(), 0, 0, null);
        }

        if (floorSprites.size() > 0){
            for (int i = 0; i < floorSprites.size(); i++) {
                g.drawImage(floorSprites.get(i).getImage(), Math.round(floorSprites.get(i).getX()),
                        Math.round(floorSprites.get(i).getY()), null);
            }
        }

        if (sprites.size() > 0) {
            for (int i = 0; i < sprites.size(); i++) {

                if (currentScene instanceof MapScene) {
                    if (sprites.get(i).getX() - ((MapScene) currentScene).getCamX() >= -150 * scaleRatioX &&
                            sprites.get(i).getX() - ((MapScene) currentScene).getCamX() < screenPixelWidth * scaleRatioX &&
                            sprites.get(i).getY() - ((MapScene) currentScene).getCamY() >= -150 * scaleRatioY &&
                            sprites.get(i).getY() - ((MapScene) currentScene).getCamY() < screenPixelHeight * scaleRatioY
                            ) {
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, sprites.get(i).getOpacity()));
                        g.drawImage(sprites.get(i).getImage(), Math.round(sprites.get(i).getX()),
                                Math.round(sprites.get(i).getY()), null);
                    }
                } else {

                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, sprites.get(i).getOpacity()));
                    g.drawImage(sprites.get(i).getImage(), Math.round(sprites.get(i).getX()),
                            Math.round(sprites.get(i).getY()), null);
                }
            }
        }

        if (messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++){

                GString mess = messages.get(i);

                if (currentScene instanceof MapScene){

                    g.setColor(Color.BLACK);
                    g.setFont(mess.getFont());
                    g.drawString(mess.getString(), Math.round(mess.getX() + ((MapScene) currentScene).getCamX() + 1 * scaleRatioX),
                            Math.round(mess.getY() + ((MapScene) currentScene).getCamY()  + 1 * scaleRatioY));

                    g.setColor(mess.getColor());
                    g.setFont(mess.getFont());
                    g.drawString(mess.getString(), Math.round(mess.getX() + ((MapScene) currentScene).getCamX() ),
                            Math.round(mess.getY() + ((MapScene) currentScene).getCamY() ));

                } else {

                    g.setColor(Color.BLACK);
                    g.setFont(mess.getFont());
                    g.drawString(mess.getString(), Math.round(mess.getX() + 1 * scaleRatioX),
                            Math.round(mess.getY() + 1 * scaleRatioY));

                    g.setColor(mess.getColor());
                    g.setFont(mess.getFont());
                    g.drawString(mess.getString(), Math.round(mess.getX()),
                            Math.round(mess.getY()));

                }
            }
        }

        if(timer.getTimePassed() > 0 && currentScene.debugMode) {

            if (currentScene instanceof MapScene){
                g.setColor(Color.BLACK);
                g.drawString("FPS: " + (int) timer.getFPS(), Math.round(0 + ((MapScene) currentScene).getCamX() + 1 * scaleRatioX),
                        Math.round(80 + ((MapScene) currentScene).getCamY() + 1 * scaleRatioY));

                g.setColor(Color.WHITE);
                g.drawString("FPS: " + (int) timer.getFPS(), Math.round(0 + ((MapScene) currentScene).getCamX()),
                        Math.round(80 + ((MapScene) currentScene).getCamY()));
            } else {
                g.setColor(Color.WHITE);
                g.drawString("FPS: " + (int) timer.getFPS(), 0, 80);
            }
        }

        if (sceneManager.isLoading()){
            if (currentScene instanceof MapScene){

                g.setColor(new Color(0, 0, 0, (float) sceneManager.getLoadOpacity()));
                g.fillRect((int)Math.floor(((MapScene) currentScene).getCamX()),
                        (int)Math.floor(((MapScene) currentScene).getCamY()),
                        screenManager.getWidth(), screenManager.getHeight());
            } else {
                g.setColor(new Color(0, 0, 0, (float) sceneManager.getLoadOpacity()));
                g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());
            }
        } else if (currentScene.isLoading()){
            if (currentScene instanceof MapScene) {

                g.setColor(new Color(0, 0, 0, (float) currentScene.getLoadOpacity()));
                g.fillRect((int)Math.floor(((MapScene) currentScene).getCamX()),
                        (int)Math.floor(((MapScene) currentScene).getCamY()),
                        screenManager.getWidth(), screenManager.getHeight());
            }
        }
    }

    private int forward_key_down = 2;
    private int back_key_down = 2;
    private int triangle_key_down = 2;
    private int square_key_down = 2;
    private int L1_key_down = 2;
    private int L2_key_down = 2;
    private int R1_key_down = 2;
    private int R2_key_down = 2;
    private int left_key_down = 2;
    private int right_key_down = 2;
    private int up_key_down = 2;
    private int down_key_down = 2;

    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE){
            stop();
        } else if (keyCode == KeyEvent.VK_M){
            screenManager.minimize();
            e.consume();
        } else if (keyCode == KeyEvent.VK_J){
            forward_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_I){
            back_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_U){
            triangle_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_H){
            square_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_K){
            L1_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_L){
            L2_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_O){
            R1_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_P){
            R2_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_A){
            left_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_D){
            right_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_W){
            up_key_down = 1;
            e.consume();
        } else if (keyCode == KeyEvent.VK_S){
            down_key_down = 1;
            e.consume();
        }
    }

    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_J){
            forward_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_I){
            back_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_U){
            triangle_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_H){
            square_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_K){
            L1_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_L){
            L2_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_O){
            R1_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_P){
            R2_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_A){
            left_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_D){
            right_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_W){
            up_key_down = 0;
            e.consume();
        } else if (keyCode == KeyEvent.VK_S){
            down_key_down = 0;
            e.consume();
        }

    }

    public void keyTyped(KeyEvent e){
        e.consume();
    }

    public void checkKeys(){
        if (forward_key_down == 1){
            currentScene.forward();
        } else if (forward_key_down == 0){
            currentScene.forwardReleased();
            forward_key_down = 2;
        }
        if (back_key_down == 1){
            currentScene.back();
        } else if (back_key_down == 0){
            currentScene.backReleased();
            back_key_down = 2;
        }
        if (triangle_key_down  == 1){
            currentScene.triangle();
        } else if (triangle_key_down == 0){
            currentScene.triangleReleased();
            triangle_key_down = 2;
        }
        if (square_key_down  == 1){
            currentScene.square();
        } else if (square_key_down == 0){
            currentScene.squareReleased();
            square_key_down = 2;
        }
        if (L1_key_down  == 1){
            currentScene.L1();
        } else if (L1_key_down == 0){
            currentScene.L1Released();
            L1_key_down = 2;
        }
        if (L2_key_down  == 1){
            currentScene.L2();
        } else if (L2_key_down == 0){
            currentScene.L2Released();
            L2_key_down = 2;
        }
        if (R1_key_down  == 1){
            currentScene.R1();
        } else if (R1_key_down == 0){
            currentScene.R1Released();
            R1_key_down = 2;
        }
        if (R2_key_down  == 1){
            currentScene.R2();
        } else if (R2_key_down == 0){
            currentScene.R2Released();
            R2_key_down = 2;
        }
        if (left_key_down  == 1){
            currentScene.left();
        } else if (left_key_down == 0){
            currentScene.leftReleased();
            left_key_down = 2;
        }
        if (right_key_down == 1){
            currentScene.right();
        } else if (right_key_down == 0){
            currentScene.rightReleased();
            right_key_down = 2;
        }
        if (up_key_down == 1){
            currentScene.up();
        } else if (up_key_down == 0){
            currentScene.upReleased();
            up_key_down = 2;
        }
        if (down_key_down == 1){
            currentScene.down();
        } else if (down_key_down == 0){
            currentScene.downReleased();
            down_key_down = 2;
        }
    }
}
