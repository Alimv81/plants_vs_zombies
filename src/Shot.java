import javax.swing.*;
import java.awt.*;

public class Shot {

    public boolean alive = true;

    public int damage = 20;

    public Image image = new ImageIcon("images/7.png").getImage();

    public Point place;

    public Shot(Point point){
        this.place = point;
    }

    public int hit(){
        alive = false;
        return damage;
    }

    public void update(){
        this.place.x += 16;
        if (place.x > 1600){
            alive = false;
        }
    }
}
