import javax.swing.*;
import java.awt.*;

public class Potato{

    public static int price = 50;

    public Point place;

    public static int last_planted = 0;

    public static int cool_down = 5000;

    public Image image = new ImageIcon("images/4.jpg").getImage();

    public int damage = 200;

    public boolean alive = true;

    public Potato(Point point) {
        place = point;
    }

    public int hit(){
        die();
        return damage;
    }

    public void die(){
        alive = false;
    }

    public static void setLast_planted(int last_planted) {
        Potato.last_planted = last_planted;
    }

    public static boolean can_be_planted(){
        return MyPanel.Now - last_planted > cool_down;
    }
}
