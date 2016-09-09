import java.awt.*;

/**
 * Created by justin on 8/22/16.
 */
public class GeneralText extends GString{

    int column, row;

    public GeneralText(String s){
        string = s;

        color = Color.WHITE;
        fontType = Font.PLAIN;
        size = 12;
        fontName = "Arial";
        setupFont();
    }

    public GeneralText(String s, int column, int row){
        string = s;
        this.column = column;
        this.row = row;

        color = Color.WHITE;
        fontType = Font.PLAIN;
        size = 12;
        fontName = "Arial";
        setupFont();
    }

    public void setColumn(int i){
        column = i;
    }

    public int getColumn(){
        return column;
    }

    public void setRow(int i){
        row = i;
    }

    public int getRow(){return row;}
}
