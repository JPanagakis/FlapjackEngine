import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * Created by justin on 5/9/16.
 */
public class GString {

    protected final double SCALE = OptionsManager.get().getScaleRatioX();

    protected String string;
    protected float x, y, dx, dy;
    protected Color color;
    protected int size;
    protected int fontType;
    protected String fontName;
    protected Font font;
    
    public GString(){
        
    }

    public GString(String string, float x, float y){
        this.string = string;
        this.x = x;
        this.y = y;

        color = Color.WHITE;
        fontType = Font.PLAIN;

        this.size = 12;

        fontName = "Arial";
        setupFont();
    }

    public GString(String string, float x, float y, Color color){
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = color;

        fontType = Font.PLAIN;

        this.size = 12;

        fontName = "Arial";
        setupFont();
    }

    public GString(String string, float x, float y, Color color, int size){
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;

        fontType = Font.PLAIN;

        fontName = "Arial";
        setupFont();
    }

    public GString(String string, float x, float y, Color color, int size, int fontType){
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.fontType = fontType;

        fontName = "Arial";
        setupFont();
    }



    public String getString(){
        return string;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public Color getColor() {return color;}

    public int getSize(){return size;}

    public int getFontType(){return fontType;}

    public String getFontName(){return fontName;}

    public void setX(float x){this.x = x;}

    public void setY(float y){this.y = y;}

    public void setString(String s){
        string = s;
    }

    public void setColor(Color c){color = c;}

    public void setSize(int size){
        this.size = size;
        setupFont();
    }

    public void setFontType(int fontType){
        this.fontType = fontType;
        setupFont();
    }

    public void setFontName(String name){
        this.fontName = name;
        setupFont();
    }


    public Font getFont(){return font;}

    public void setFont(Font font){this.font = font;}

    public void setupFont(){

        size = (int)Math.round(size * SCALE);
        font = new Font(fontName, fontType, size);
    }

    public int getWidth(){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textwidth = (int)(font.getStringBounds(string, frc).getWidth());
        return textwidth;
    }

    public int getHeight(){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textheight = (int)(font.getStringBounds(string, frc).getHeight());
        return textheight;
    }

    public void move(){
        x += dx;
        y += dy;
    }
}
