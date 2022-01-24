import javax.swing.*;
import java.awt.*;

public class Zombie2 {

    public boolean stop = false;

    public static int last_planted = 0;

    public static int price = 37;

    public int last_hit = 0;

    public static int hitting_cool_down = 1000;

    public static int cool_down_time = 2000;

    public boolean alive = true;

    public Image image = new ImageIcon("images/5.jpg").getImage();;

    public Point place;

    public int Hp = 450;
    public int damage  = 45;

    public Zombie2(Point Point){

        this.place = Point;

    }

    public void die(){
        alive = false;
    }

    public void update(){
        this.place.x -= 1;
    }

    public int hit(){
        return damage;
    }

    public void get_hit(int x){
        Hp -= x;

        if (Hp <= 0){
            die();
        }
    }

    public static void setLast_planted(int planted){
        last_planted = planted;
    }

    public static boolean can_be_planted(){
        return MyPanel.Now -last_planted > cool_down_time;
    }

    public boolean should_hit(){
        return MyPanel.Now - last_hit > hitting_cool_down;
    }

    public void setLast_hit(){
        last_hit = MyPanel.Now;
    }
}
