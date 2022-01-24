import java.util.Random;

public class RandomManager {

    static Random random = new Random();

    public static Integer getRandom(){
        return random.nextInt(5);
    }

    public static void main(String[] args) {
        System.out.println(getRandom());
    }
}
