import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyPanel extends JPanel implements Runnable {

    public static int Now;

    boolean Online;
    boolean Host;

    public int amount_of_son;
    public int doral;
    public int number_of_zombies;

    String dragging = "n";

    ArrayList<Zombies> zombies = new ArrayList<>();
    ArrayList<Plants> plants = new ArrayList<>();
    ArrayList<Shot> shots = new ArrayList<>();
    ArrayList<Potato> potatoes = new ArrayList<>();
    ArrayList<Sun> suns = new ArrayList<>();
    ArrayList<Zombie2> zombies2 = new ArrayList<>();
    ArrayList<Plants2> plants2 = new ArrayList<>();

    static Socket socket, socket2;
    static ServerSocket serverSocket;

    static DataInputStream inputStream, inputStream2;
    static DataOutputStream outputStream, outputStream2;

    boolean did_player_plant_something = false, did_the_other_player_plant_something = false;
    int type_of_planted_object, type_of_planted_object_by_other_player;
    Point where_player_planted_point = new Point(), where_other_player_planted_point = new Point();

    boolean running = true;

    Image img;

    MyPanel(boolean a, boolean b) {
        Online = a;
        Host = b;

        amount_of_son = Main.son;
        doral = Main.doral;
        number_of_zombies = Main.number_of_zombies;


        ClicksListener clicksListener = new ClicksListener();
        this.addMouseListener(clicksListener);

        if (Online) {
            if (Host)
                make_server();
            else
                make_socket();
        }
    }


    public void Draw() {
        Graphics2D graphics2D = (Graphics2D) getGraphics();

        img = new ImageIcon("images/back.png").getImage();
        graphics2D.drawImage(img, 0, 0, null);

        this.setSize(1980, 1280);

        if (!Host){
            graphics2D.setPaint(new Color(0xBABD17));
            graphics2D.setFont(new Font("TimesRoman", Font.ITALIC, 30));
            graphics2D.drawString("Sons: " + amount_of_son, 100, 80);

        }
        else{
            graphics2D.setPaint(new Color(0xBABD17));
            graphics2D.setFont(new Font("TimesRoman", Font.ITALIC, 30));
            graphics2D.drawString("Dorals: " + doral, 100, 80);

        }

        graphics2D.setPaint(new Color(0xBABD17));
        graphics2D.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        graphics2D.drawString("Zombies Left: " + number_of_zombies, 400, 80);

        if (Host) {
            img = new ImageIcon("images/3.png").getImage();
            graphics2D.drawImage(img, 0, 238, null);


            img = new ImageIcon("images/5.jpg").getImage();
            graphics2D.drawImage(img, 0, 502, null);
        } else {

            img = new ImageIcon("images/2.png").getImage();
            graphics2D.drawImage(img, 0, 150, null);

            img = new ImageIcon("images/1.jpg").getImage();
            graphics2D.drawImage(img, 0, 590, null);

            img = new ImageIcon("images/4.jpg").getImage();
            graphics2D.drawImage(img, 0, 326, null);

            img = new ImageIcon("images/6.png").getImage();
            graphics2D.drawImage(img, 0, 414, null);

        }


        for (Zombies zombie : zombies) {
            if (zombie.alive) {

                graphics2D.drawImage(zombie.image, zombie.place.x, zombie.place.y, null);

                graphics2D.setPaint(new Color(0x1818C1));
                graphics2D.setStroke(new BasicStroke(4));

                graphics2D.drawLine(zombie.place.x, zombie.place.y - 10, (zombie.place.x) + 70 * zombie.Hp / 300, zombie.place.y - 10);

                if (zombie.place.x <= 400){
                    running = false;
                }
            }
        }

        for (Plants plant : plants) {
            if (plant.alive) {
                graphics2D.setPaint(new Color(0xD90A0A));
                graphics2D.setStroke(new BasicStroke(4));

                graphics2D.drawLine(plant.place.x, plant.place.y - 10, plant.place.x + 70 * plant.Hp / 200, plant.place.y - 10);


                graphics2D.drawImage(plant.image, plant.place.x, plant.place.y, null);
            }
        }

        for (Shot shot : shots) {
            if (shot.alive) {
                graphics2D.drawImage(shot.image, shot.place.x, shot.place.y, null);
            }
        }

        for (Potato potato : potatoes) {
            if (potato.alive) {
                graphics2D.drawImage(potato.image, potato.place.x, potato.place.y, null);
            }
        }

        for (Sun sun : suns) {
            if (sun.alive) {

                graphics2D.setPaint(new Color(0xD90A0A));
                graphics2D.setStroke(new BasicStroke(4));

                graphics2D.drawLine(sun.place.x, sun.place.y - 10, sun.place.x + 70 * sun.Hp / 100, sun.place.y - 10);

                graphics2D.drawImage(sun.image, sun.place.x, sun.place.y, null);
            }
        }

        for (Zombie2 zombie : zombies2) {
            if (zombie.alive) {
                graphics2D.drawImage(zombie.image, zombie.place.x, zombie.place.y, null);

                graphics2D.setPaint(new Color(0x1818C1));
                graphics2D.setStroke(new BasicStroke(4));

                graphics2D.drawLine(zombie.place.x, zombie.place.y - 10, zombie.place.x + 70 * zombie.Hp / 450, zombie.place.y - 10);

                if (zombie.place.x <= 400){
                    running = false;
                }
            }
        }

        for (Plants2 plant : plants2) {
            if (plant.alive) {

                graphics2D.setPaint(new Color(0xD90A0A));
                graphics2D.setStroke(new BasicStroke(4));

                graphics2D.drawLine(plant.place.x, plant.place.y - 10, plant.place.x + 70 * plant.Hp / 300, plant.place.y - 10);

                graphics2D.drawImage(plant.image, plant.place.x, plant.place.y, null);
            }
        }
    }

    @Override
    public void run() {
        while (running) {

            update();
            Draw();


            Now += 50;

            if (Now % 10000 == 0) {
                amount_of_son += 25;
            }

            if (Now % 3000 == 0){
                doral += 9;
            }


            if (Online) {
                try {
                    connect();
                    update_for_online();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(Now % 8000 == 0 && number_of_zombies != 0){
                update_for_offline();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {

        for (Zombies zombie : zombies) {
            if (zombie.alive) {
                for (Plants plant : plants) {
                    if (plant.place.y == zombie.place.y && plant.alive) {
                        if (plant.should_shoot()) {
                            shots.add(new Shot(new Point(plant.place.x, plant.place.y + 20)));
                            plant.setLast_shot(Now);
                        }

                        if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x && plant.alive) {

                            zombie.stop = true;
                        }
                    }

                }

                for (Plants2 plant : plants2) {
                    if (plant.place.y == zombie.place.y && plant.alive) {
                        if (plant.should_shoot()) {
                            shots.add(new Shot(new Point(plant.place.x, plant.place.y + 20)));
                            shots.add(new Shot(new Point(plant.place.x - 20, plant.place.y + 20)));
                            plant.setLast_shot(Now);
                        }

                        if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x && plant.alive) {

                            zombie.stop = true;
                        }
                    }

                }

                for (Sun sun : suns) {
                    if (sun.alive && sun.place.y == zombie.place.y) {
                        if (sun.place.x + 70 > zombie.place.x && sun.place.x < zombie.place.x) {

                            zombie.stop = true;
                        }
                    }
                }
                for (Shot shot : shots) {
                    if (shot.place.y - 20 == zombie.place.y && shot.alive) {
                        if (shot.place.x + 10 > zombie.place.x && shot.place.x - 10 < zombie.place.x) {
                            zombie.get_hit(shot.hit());
                        }
                    }
                }
                if (!zombie.stop) {
                    zombie.update();
                } else {
                    boolean is_there = false;
                    for (Plants plant : plants) {
                        if (plant.place.y == zombie.place.y && plant.alive) {
                            if (zombie.should_hit()) {
                                if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    plant.get_hit(zombie.hit());
                                    if (!plant.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }

                    for (Plants2 plant : plants2) {
                        if (plant.place.y == zombie.place.y && plant.alive) {
                            if (zombie.should_hit()) {
                                if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    plant.get_hit(zombie.hit());
                                    if (!plant.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }

                    for (Sun sun : suns) {
                        if (sun.place.y == zombie.place.y && sun.alive) {
                            if (zombie.should_hit()) {
                                if (sun.place.x + 70 > zombie.place.x && sun.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    sun.get_hit(zombie.hit());
                                    if (!sun.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }
                    if (!is_there) {
                        zombie.stop = false;
                    }
                }
            }
        }

        for (Zombie2 zombie : zombies2) {
            if (zombie.alive) {
                for (Plants plant : plants) {
                    if (plant.place.y == zombie.place.y && plant.alive) {
                        if (plant.should_shoot()) {
                            shots.add(new Shot(new Point(plant.place.x, plant.place.y + 20)));
                            plant.setLast_shot(Now);
                        }

                        if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x && plant.alive) {

                            zombie.stop = true;
                        }
                    }

                }

                for (Plants2 plant : plants2) {
                    if (plant.place.y == zombie.place.y && plant.alive) {
                        if (plant.should_shoot()) {
                            shots.add(new Shot(new Point( plant.place.x, plant.place.y + 20)));
                            shots.add(new Shot(new Point( plant.place.x - 20, plant.place.y + 20)));
                            plant.setLast_shot(Now);
                        }

                        if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x && plant.alive) {

                            zombie.stop = true;
                        }
                    }

                }

                for (Sun sun : suns) {
                    if (sun.alive && sun.place.y == zombie.place.y) {
                        if (sun.place.x + 70 > zombie.place.x && sun.place.x < zombie.place.x) {

                            zombie.stop = true;
                        }
                    }
                }
                for (Shot shot : shots) {
                    if (shot.place.y - 20 == zombie.place.y && shot.alive) {
                        if (shot.place.x + 10 > zombie.place.x && shot.place.x - 10 < zombie.place.x) {
                            zombie.get_hit(shot.hit());
                        }
                    }
                }
                if (!zombie.stop) {
                    zombie.update();
                } else {
                    boolean is_there = false;

                    for (Plants plant : plants) {
                        if (plant.place.y == zombie.place.y && plant.alive) {
                            if (zombie.should_hit()) {
                                if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    plant.get_hit(zombie.hit());
                                    if (!plant.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }

                    for (Plants2 plant : plants2) {
                        if (plant.place.y == zombie.place.y && plant.alive) {
                            if (zombie.should_hit()) {
                                if (plant.place.x + 70 > zombie.place.x && plant.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    plant.get_hit(zombie.hit());
                                    if (!plant.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }

                    for (Sun sun : suns) {
                        if (sun.place.y == zombie.place.y && sun.alive) {
                            if (zombie.should_hit()) {
                                if (sun.place.x + 70 > zombie.place.x && sun.place.x < zombie.place.x) {
                                    is_there = true;
                                    zombie.setLast_hit();
                                    sun.get_hit(zombie.hit());
                                    if (!sun.alive) {
                                        zombie.stop = false;
                                    }
                                }
                            }
                        }
                    }
                    if (!is_there) {
                        zombie.stop = false;
                    }
                }
            }
        }

        for (Shot shot : shots) {
            shot.update();
        }

        for (Potato potato : potatoes) {
            for (Zombies zombie : zombies) {
                if (potato.place.y == zombie.place.y && potato.place.x + 60 > zombie.place.x && potato.place.x - 60 < zombie.place.x && potato.alive && zombie.alive) {
                    zombie.die();
                    potato.die();
                }
            }

            for (Zombie2 zombie : zombies2) {
                if (potato.place.y == zombie.place.y && potato.place.x + 60 > zombie.place.x && potato.place.x - 60 < zombie.place.x && potato.alive && zombie.alive) {
                    zombie.die();
                    potato.die();
                }
            }
        }

        for (Sun sun : suns) {
            if (sun.alive && sun.should_give()) {
                amount_of_son += Sun.give;
                sun.setLast_give(Now);
            }
        }
    }

    public void update_for_online() {
        if (Host) {
            if (did_the_other_player_plant_something) {
                int type = type_of_planted_object_by_other_player;
                Point point = new Point(where_other_player_planted_point);

                if (type == 0){
                    plants.add(new Plants(new Point(point)));
                }
                else if (type == 1){
                    potatoes.add(new Potato(new Point(point)));
                }
                else if (type == 2){
                    suns.add(new Sun(new Point(point)));
                }
                else {
                    plants2.add(new Plants2(new Point(point)));
                }
            }
        } else {
            if (did_player_plant_something) {
                number_of_zombies --;

                int type = type_of_planted_object;
                Point point = new Point(where_player_planted_point);

                if (type == 0){
                    zombies.add(new Zombies(new Point(point)));
                }
                else{
                    zombies2.add(new Zombie2(new Point(point)));
                }
            }
        }

        did_player_plant_something = false;
        did_the_other_player_plant_something = false;
    }

    public void update_for_offline(){
        int type = RandomManager.getRandom() % 2;
        int line = RandomManager.getRandom() % 5;

        Point point = new Point();

        if (line == 0) {
            point.y = 220;
        } else if (line == 1) {
            point.y = 320;
        } else if (line == 2) {
            point.y = 420;
        } else if (line == 3) {
            point.y = 520;
        } else {
            point.y = 630;
        }

        point.x = 1500;

        if (type == 0){
            zombies.add(new Zombies(new Point(point)));
        }
        else {
            zombies2.add(new Zombie2(new Point(point)));
        }

        number_of_zombies --;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Thread animationThread = new Thread(this);
        animationThread.start();
    }

    static void make_socket() {
        try {
            socket = new Socket("localhost", 8765);

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void make_server() {
        try {
            serverSocket = new ServerSocket(8765);

            socket2 = serverSocket.accept();

            inputStream2 = new DataInputStream(socket2.getInputStream());
            outputStream2 = new DataOutputStream(socket2.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        if (Host) {
            outputStream2.writeBoolean(did_player_plant_something);
            if (did_player_plant_something) {
                outputStream2.writeInt(type_of_planted_object);
                outputStream2.writeInt(where_player_planted_point.x);
                outputStream2.writeInt(where_player_planted_point.y);
            }
            did_the_other_player_plant_something = inputStream2.readBoolean();

            if (did_the_other_player_plant_something) {
                type_of_planted_object_by_other_player = inputStream2.readInt();
                where_other_player_planted_point.x = inputStream2.readInt();
                where_other_player_planted_point.y = inputStream2.readInt();
            }
        } else {
            did_player_plant_something = inputStream.readBoolean();

            if (did_player_plant_something) {
                type_of_planted_object = inputStream.readInt();
                where_player_planted_point.x = inputStream.readInt();
                where_player_planted_point.y = inputStream.readInt();
            }

            outputStream.writeBoolean(did_the_other_player_plant_something);
            if (did_the_other_player_plant_something) {
                outputStream.writeInt(type_of_planted_object_by_other_player);
                outputStream.writeInt(where_other_player_planted_point.x);
                outputStream.writeInt(where_other_player_planted_point.y);
            }
        }

    }

    class ClicksListener extends MouseAdapter {

        Point previous;

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            previous = e.getPoint();

            if (previous.x < 70 && 150 < previous.y) {
                if (previous.y < 238) {
                    dragging = "p";
                } else if (previous.y < 326) {
                    dragging = "z";
                } else if (previous.y < 414) {
                    dragging = "p1";
                } else if (previous.y < 502) {
                    dragging = "s";
                } else if (previous.y < 590) {
                    dragging = "z2";
                }
                else if (previous.y < 678){
                    dragging = "p2";
                }
            }


        }

        @Override
        public void mouseReleased(MouseEvent event) {
            super.mouseReleased(event);

            Point point = event.getPoint();

            if (point.y < 300) {
                point.y = 220;
            } else if (point.y < 400) {
                point.y = 320;
            } else if (point.y < 500) {
                point.y = 420;
            } else if (point.y < 600) {
                point.y = 520;
            } else {
                point.y = 630;
            }

            if (point.x < 400) {
                point.x = 400;
            }

            switch (dragging) {
                case "n":
                    break;

                case "z":
                    point.x = 1500;

                    if (doral >= Zombies.price && Zombies.can_be_planted() && Host && number_of_zombies != 0) {
                        zombies.add(new Zombies(point));
                        doral -= Zombies.price;
                        Zombies.setLast_planted(Now);
                        did_player_plant_something = true;
                        where_player_planted_point = point;
                        type_of_planted_object = 0;
                        number_of_zombies --;
                    }
                    break;
                case "p":
                    if (amount_of_son >= Plants.price && Plants.can_be_planted() && !Host) {
                        plants.add(new Plants(point));
                        amount_of_son -= Plants.price;
                        Plants.setLast_planted(Now);
                        did_the_other_player_plant_something = true;
                        where_other_player_planted_point = point;
                        type_of_planted_object_by_other_player = 0;
                    }
                    break;
                case "p1":
                    if (amount_of_son >= Potato.price && Potato.can_be_planted() && !Host) {
                        potatoes.add(new Potato(point));
                        amount_of_son -= Potato.price;
                        Plants.setLast_planted(Now);
                        did_the_other_player_plant_something = true;
                        where_other_player_planted_point = point;
                        type_of_planted_object_by_other_player = 1;
                    }
                    break;
                case "s":
                    if (amount_of_son >= Sun.price && Sun.can_be_planted() && !Host) {
                        suns.add(new Sun(point));
                        amount_of_son -= Sun.price;
                        Sun.setLast_planted();
                        did_the_other_player_plant_something = true;
                        where_other_player_planted_point = point;
                        type_of_planted_object_by_other_player = 2;
                    }
                    break;
                case "z2":
                    point.x = 1500;

                    if (doral >= Zombie2.price && Zombie2.can_be_planted() && Host && number_of_zombies != 0) {
                        zombies2.add(new Zombie2(point));
                        doral -= Zombie2.price;
                        Zombie2.setLast_planted(Now);
                        did_player_plant_something = true;
                        where_player_planted_point = point;
                        type_of_planted_object = 1;
                        number_of_zombies --;
                    }

                    break;
                case "p2":
                    if (amount_of_son >= Plants2.price && Plants2.can_be_planted() && !Host){
                        plants2.add(new Plants2(point));
                        amount_of_son -= Plants2.price;
                        Plants2.setLast_planted(Now);
                        did_the_other_player_plant_something = true;
                        where_other_player_planted_point = point;
                        type_of_planted_object_by_other_player = 3;
                    }

                    break;
            }


            dragging = "n";
        }
    }
}



