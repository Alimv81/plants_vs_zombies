import javax.swing.*;
import java.awt.*;

public class Sun {

        public static int price = 40;

        public static int give = 25;

        public int last_give = 0;

        public int give_cool_down = 7000;

        public static int cool_down = 2000;

        public static int last_planted = 0;

        public Image image = new ImageIcon("images/6.png").getImage();

        public boolean alive = true;

        public int Hp = 100;

        public Point place;

        public Sun(Point point){
            place = point;
            last_give = MyPanel.Now;
        }

        public int update(){
            return 20;
        }

        public void die(){
            alive = false;
        }

        public static boolean can_be_planted(){
            return MyPanel.Now - last_planted > cool_down;
        }

        public boolean should_give(){
            return MyPanel.Now - last_give > give_cool_down;
        }

        public void setLast_give(int shot){
            last_give = shot;
        }

        public static void setLast_planted(){
            last_planted = MyPanel.Now;
        }

        public void get_hit(int hit){
            Hp -= hit;

            if (Hp <= 0){
                alive = false;
            }
        }
}
