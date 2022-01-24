import javax.swing.*;
import java.awt.*;

public class Plants2 {

    public static int price = 30;

    public int last_shot = 0;

    public int hitting_cool_down = 7000;

    public boolean alive = true;

    public static int cool_down = 0;

    public static int last_planted = 0;

    public Image image = new ImageIcon("images/1.jpg").getImage();

    public Point place;

    public int Hp = 300;

    public void die(){
        alive = false;
    }

    public Plants2(Point point){
        place = point;

    }

    public void get_hit(int a){
        Hp -= a;

        if(Hp <= 0){
            die();
        }
    }

    public static void setLast_planted(int planted){
        last_planted = planted;
    }

    public static boolean can_be_planted(){
        return MyPanel.Now -last_planted > cool_down;
    }

    public boolean should_shoot(){
        return MyPanel.Now - last_shot > hitting_cool_down;
    }

    public void setLast_shot(int shot){
        last_shot = shot;
    }

}

