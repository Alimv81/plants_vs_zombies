import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    MyPanel myPanel;

    public MyFrame(boolean a, boolean b){
        myPanel = new MyPanel(a, b);

        this.setTitle("plants vs zombies");

        this.add(myPanel);

        this.getContentPane().setBackground(new Color(49, 68, 165));

        this.setIconImage(new ImageIcon("first.jpg").getImage());
        this.pack();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
