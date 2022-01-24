import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static int son = 50;
    public static int doral = 20;
    public static int number_of_zombies = 20;


    public static boolean small_window = true, back_image = true;
    public static JFrame frame = new JFrame("Plants Versus Zombies");


    public static void reset() throws IOException {
        frame.setVisible(false);
        frame = null;

        if (small_window){
            frame = new JFrame("plants_vs_zombies");
            frame.setBounds(590,200,300,400);
            if (back_image){
                   JLabel label = new JLabel();
                   label.setIcon(new ImageIcon(ImageIO.read(new File("images/first.jpg"))));
                   label.setBounds(0,0,300,400);
                   frame.add(label);
            }
            else {
                frame.getContentPane().setBackground(new Color(64, 59, 220));
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setVisible(true);
        }
    }

    public static void show(int a) throws IOException {
        switch (a) {
            case -1 -> {
                reset();

                JButton button = new JButton("Next");

                JLabel label = new JLabel("amount of zombies:");
                JLabel label1 = new JLabel("amount of dorals:");
                JLabel label2 = new JLabel("amount of sons:");

                JLabel label3 = new JLabel(String.valueOf(number_of_zombies));
                JLabel label4 = new JLabel(String.valueOf(doral));
                JLabel label5 = new JLabel(String.valueOf(son));

                JSlider slider = new JSlider(0,100,number_of_zombies);
                JSlider slider1 = new JSlider(0,100,doral);
                JSlider slider2 = new JSlider(0,100,son);

                label.setBounds(10,5,150,10);
                slider.setBounds(10,20,200,70);
                label3.setBounds(250,40,50,10);

                label1.setBounds(10,105,150,10);
                slider1.setBounds(10,120,200,70);
                label4.setBounds(250,140,50,10);

                label2.setBounds(10,205,150,10);
                slider2.setBounds(10,220,200,70);
                label5.setBounds(250,240,50,10);

                button.setBounds(210,305,80,50);

                slider.addChangeListener(e -> {
                    number_of_zombies = slider.getValue();
                    label3.setText(String.valueOf(number_of_zombies));
                });

                slider1.addChangeListener(e -> {
                    doral = slider1.getValue();
                    label4.setText(String.valueOf(doral));
                });

                slider2.addChangeListener(e -> {
                    son =  slider2.getValue();
                    label5.setText(String.valueOf(son));
                });

                button.addActionListener(e -> {
                    try {
                        small_window = false;
                        back_image = false;

                        show(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                frame.add(button);

                frame.add(label);
                frame.add(label1);
                frame.add(label2);

                frame.add(label3);
                frame.add(label4);
                frame.add(label5);

                frame.add(slider);
                frame.add(slider1);
                frame.add(slider2);
            }

            case 0 -> {
                reset();

                JButton button = new JButton("Play");
                JButton button1 = new JButton("Setting");
                JButton button2 = new JButton("Exit");

                button.setBounds(100, 40, 100, 60);
                button1.setBounds(100, 150, 100, 60);
                button2.setBounds(100, 260, 100, 60);

                button.addActionListener(e -> {
                    try {
                        small_window = false;
                        back_image = false;

                        show(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                button1.addActionListener(e -> {
                    try {
                        back_image = false;

                        show(-1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                button2.addActionListener(e -> System.exit(0));

                frame.add(button);
                frame.add(button1);
                frame.add(button2);
            }

            case 1 -> {
                reset();

                JButton button3 = new JButton("Client");
                JButton button4 = new JButton("Offline");
                JButton button5 = new JButton("Host");

                button3.setBounds(100, 150, 100, 60);
                button4.setBounds(100, 40, 100, 60);
                button5.setBounds(100, 260, 100, 60);

                button3.addActionListener(e -> {
                    try {

                        show(3);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                button4.addActionListener(e -> {
                    try {

                        show(0);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                button5.addActionListener(e -> {
                    try {

                        show(4);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                });

                frame.add(button3);
                frame.add(button4);
                frame.add(button5);
            }

            case 2 -> {
                frame.setVisible(false);
                frame = null;

                new MyFrame(false, false);
            }

            case 3 -> {
                frame.setVisible(false);
                frame = null;

                new MyFrame(true, false);
            }
            case 4 -> {
                frame.setVisible(false);
                frame = null;

                new MyFrame(true, true);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        show(1);
    }
}
