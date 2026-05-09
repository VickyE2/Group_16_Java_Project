package group.sixteen.bowen;

public class Main implements Cloneable {
    public static String name = "Victor";

    public static void main(String... args) {
        print(name);
        print("My name is bola.\n \rI am 13 years old. \\");
    }

    public static void print(Object message) {
        System.out.println(message);
    }
}