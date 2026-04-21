package group.sixteen.bowen;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProjectOne {
    static final List<String> units = List.of(
            "Length", "Mass", "Time", "Temperature"
    );
    static boolean running = true;
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        print("=============================================");
        print("     This is Group 16's Java Project One     ");
        print("               Unit Converter                ");
        print("=============================================");

        AtomicInteger index = new AtomicInteger(1);
        var menu = units.stream().map(item -> index.getAndIncrement() + " - " + item)
                .collect(Collectors.joining("\n"));

        while (running) {
            print("\n");
            print("What unit of measurement would you like to convert from?");
            print(menu);
            print("5 - Exit");

            var option = input(">> ");

            switch (option) {
                case "1" -> beginLengthConversion();
                case "2" -> beginMassConversion();
                case "3" -> beginTimeConversion();
                case "4" -> beginTemperatureConversion();
                case "5" -> {
                    print("Quitting Application");
                    running = false;
                }
            }
        }

    }

    /*
    This block is for length conversion
     */
    public static void beginLengthConversion() {
        var localRunning = true;
        List<String> options = List.of("km", "m", "cm", "mm", "in", "f", "mi");
        while (localRunning) {
            print("\n");
            print("Available units of length:\n - Kilometers[KM], Meters[M], Centimeters[CM], Millimeters[MM]\n " +
                    "- Inches[IN], Feet[F]\n - Miles[MI]");
            var from = input("What unit of length would you like to convert from? (use the letters in square braces - not case sensitive) >> ").toLowerCase();
            if (!options.contains(from)) {
                print("Invalid input: " + from);
            }
            var to = input("What unit of length would you like to convert to >> ").toLowerCase();
            if (!options.contains(to)) {
                print("Invalid input: " + to);
            }
            var rawValue = input("What is the value to convert >>");
            double value;
            try {
                value = Double.parseDouble(rawValue);
                if (from.equals(to)) {
                    print("From and To were the same [" + from + "] so value remains: " + value);
                    break;
                }
                switch (to) {
                    case "km" -> convertToKm(value, from);
                    case "m" -> convertToM(value, from);
                    case "cm" -> convertToCm(value, from);
                    case "mm" -> convertToMm(value, from);
                    case "in" -> convertToIn(value, from);
                    case "f" -> convertToFt(value, from);
                    case "mi" -> convertToMi(value, from);
                }
                localRunning = false;
            }
            catch (NumberFormatException e) {
                print("Invalid input: " + rawValue + ", should be a number.");
            }
        }
    }
    public static void convertToKm(double value, String from) {
        var result = value;
        switch (from) {
            case "m" -> result = value / 1000;
            case "cm" -> result = value / 100000;
            case "mm" -> result = value / 1000000;
            case "in" -> {
            }
            case "f" -> {
            }
            case "mi" -> result = value * 1.609;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to kilometers yielded %.4fKm%n", value, from, result);
    }
    public static void convertToMi(double value, String from) {
        var result = value;
        switch (from) {
            case "m" -> result = value / 1609;
            case "cm" -> result = value / 160900;
            case "mm" -> result = value / 1609000;
            case "in" -> {
            }
            case "f" -> {
            }
            case "km" -> result = value / 1.609;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to miles yielded %.4fMi%n", value, from, result);
    }
    public static void convertToM(double value, String from) {
        var result = value;
        switch (from) {
            case "km" -> result = value * 1000;
            case "cm" -> result = value / 100;
            case "mm" -> result = value / 1000;
            case "in" -> {
            }
            case "f" -> {
            }
            case "mi" -> result = value * 1609;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to meters yielded %.4fm%n", value, from, result);
    }
    public static void convertToCm(double value, String from) {
        var result = value;
        switch (from) {
            case "km" -> result = value * 100000;
            case "m" -> result = value * 100;
            case "mm" -> result = value / 10;
            case "in" -> {
            }
            case "f" -> {
            }
            case "mi" -> result = value * 160900;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to centimeters yielded %.4fcm%n", value, from, result);
    }
    public static void convertToMm(double value, String from) {
        var result = value;
        switch (from) {
            case "km" -> result = value * 1000000;
            case "m" -> result = value * 1000;
            case "cm" -> result = value * 10;
            case "in" -> {
            }
            case "f" -> {
            }
            case "mi" -> result = value * 1609000;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to millimeters yielded %.4fmm%n", value, from, result);
    }
    public static void convertToIn(double value, String from) {
        var result = value;
        switch (from) {
            case "km" -> result = (value * 0.0254) / 1000;
            case "m" -> result = value * 0.0254;
            case "cm" -> result = (value * 0.0254) * 100;
            case "mm" -> result = (value * 0.0254) * 1000;
            case "f" -> result = value / 12;
            case "mi" -> result = ((value * 0.0254) / 1000) * 1.609;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to inches yielded %.4fin%n", value, from, result);
    }
    public static void convertToFt(double value, String from) {
        var result = value;
        switch (from) {
            case "km" -> result = (value * 12 * 0.0254) / 1000;
            case "m" -> result = value * 12 * 0.0254;
            case "cm" -> result = (value * 12 * 0.0254) * 100;
            case "mm" -> result = (value * 12 * 0.0254) * 1000;
            case "in" -> result = value * 12;
            case "mi" -> result = ((value * 12 * 0.0254) / 1000) * 1.609;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        }
        printf("Conversion of %s%s to feet yielded %.4ffeet%n", value, from, result);
    }

    /*
    This block is for MASS conversion....
    not mass conversion like multiple but mass conversion as in weight
     */
    public static void beginMassConversion() {
        var localRunning = true;
        List<String> options = List.of("kg", "g", "ib", "oz");
        while (localRunning) {
            print("\n");
            print("Available units of mass:\n - Kilogrammes[KG], Grammes[G]\n " +
                    "- Pounds [IB], Ounces[OZ]");
            var from = input("What unit of mass would you like to convert from? (use the letters in square braces - not case sensitive) >> ").toLowerCase();
            if (!options.contains(from)) {
                print("Invalid input: " + from);
            }
            var to = input("What unit of mass would you like to convert to >> ").toLowerCase();
            if (!options.contains(to)) {
                print("Invalid input: " + to);
            }
            var rawValue = input("What is the value to convert >>");
            double value;
            try {
                value = Double.parseDouble(rawValue);
                if (from.equals(to)) {
                    print("From and To were the same [" + from + "] so value remains: " + value);
                    break;
                }
                switch (to) {
                    case "kg" -> convertToKg(value, from);
                    case "g" -> convertToG(value, from);
                    case "ib" -> convertToIb(value, from);
                    case "oz" -> convertToOz(value, from);
                }
                localRunning = false;
            }
            catch (NumberFormatException e) {
                print("Invalid input: " + rawValue + ", should be a number.");
            }
        }
    }
    public static void convertToKg(double value, String from) {
        double result;
        switch (from) {
            case "g" -> result = value / 1000;
            case "ib" -> result = value / 2.20462;
            case "oz" -> result = (value / 2.20462) / 16;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to kilogrammes yielded %.4fKg%n", value, from, result);
    }
    public static void convertToG(double value, String from) {
        double result;
        switch (from) {
            case "kg" -> result = value * 1000;
            case "ib" -> result = value * 1000 * 2.20462;
            case "oz" -> result = value * 1000 * 2.20462 * 16;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to grammes yielded %.4fg%n", value, from, result);
    }
    public static void convertToIb(double value, String from) {
        double result;
        switch (from) {
            case "kg" -> result = value * 2.20462;
            case "g" -> result = value * 2.20462 / 1000;
            case "oz" -> result = value / 16;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to pounds yielded %.4fIb%n", value, from, result);
    }
    public static void convertToOz(double value, String from) {
        double result;
        switch (from) {
            case "kg" -> result = value / 16 / 2.20462;
            case "g" -> result = value / 16 / 2.20462 * 1000;
            case "ib" -> result = value / 16;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to ounces yielded %.4foz%n", value, from, result);
    }

    /*
    This block is for TIME conversion... I should work on optimizations here... NAHHH
     */
    public static void beginTimeConversion() {
        var localRunning = true;
        List<String> options = List.of("h", "m", "s", "ms");
        while (localRunning) {
            print("\n");
            print("Available units of time:\n - Hours[H], Minutes[M], Seconds[S], Milliseconds[MS]");
            var from = input("What unit of time would you like to convert from? (use the letters in square braces - not case sensitive) >> ").toLowerCase();
            if (!options.contains(from)) {
                print("Invalid input: " + from);
            }
            var to = input("What unit of time would you like to convert to >> ").toLowerCase();
            if (!options.contains(to)) {
                print("Invalid input: " + to);
            }
            var rawValue = input("What is the value to convert >>");
            double value;
            try {
                value = Double.parseDouble(rawValue);
                if (from.equals(to)) {
                    print("From and To were the same [" + from + "] so value remains: " + value);
                    break;
                }
                switch (to) {
                    case "h" -> convertToH(value, from);
                    case "m" -> convertToMt(value, from);
                    case "s" -> convertToS(value, from);
                    case "ms" -> convertToMs(value, from);
                }
                localRunning = false;
            }
            catch (NumberFormatException e) {
                print("Invalid input: " + rawValue + ", should be a number.");
            }
        }
    }
    public static void convertToH(double value, String from) {
        double result;
        switch (from) {
            case "m" -> result = value / 60;
            case "s" -> result = value / 60 / 60;
            case "ms" -> result = value / 1000 / 60 / 60;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to hours yielded %.4fhours%n", value, from, result);
    }
    public static void convertToMt(double value, String from) {
        double result;
        switch (from) {
            case "h" -> result = value * 60;
            case "s" -> result = value / 60;
            case "ms" -> result = value / 1000 / 60;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to minutes yielded %.4fm%n", value, from, result);
    }
    public static void convertToS(double value, String from) {
        double result;
        switch (from) {
            case "h" -> result = value * 60 * 60;
            case "m" -> result = value * 60;
            case "ms" -> result = value / 1000;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to seconds yielded %.4fs%n", value, from, result);
    }
    public static void convertToMs(double value, String from) {
        double result;
        switch (from) {
            case "h" -> result = value * 60 * 60 * 1000;
            case "m" -> result = value * 60 * 1000;
            case "s" -> result = value * 1000;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to milliseconds yielded %.4fms%n", value, from, result);
    }

    /*
    This block is for TEMPERATURE conversion.
     */
    public static void beginTemperatureConversion() {
        var localRunning = true;
        List<String> options = List.of("c", "f", "k");
        while (localRunning) {
            print("\n");
            print("Available units of temperature:\n - Celsius[C], Fahrenheit[F], Kelvin[K]");
            var from = input("What unit of temperature would you like to convert from? (use the letters in square braces - not case sensitive) >> ").toLowerCase();
            if (!options.contains(from)) {
                print("Invalid input: " + from);
            }
            var to = input("What unit of temperature would you like to convert to >> ").toLowerCase();
            if (!options.contains(to)) {
                print("Invalid input: " + to);
            }
            var rawValue = input("What is the value to convert >>");
            double value;
            try {
                value = Double.parseDouble(rawValue);
                if (from.equals(to)) {
                    print("From and To were the same [" + from + "] so value remains: " + value);
                    break;
                }
                switch (to) {
                    case "c" -> convertToC(value, from);
                    case "f" -> convertToF(value, from);
                    case "k" -> convertToK(value, from);
                }
                localRunning = false;
            }
            catch (NumberFormatException e) {
                print("Invalid input: " + rawValue + ", should be a number.");
            }
        }
    }
    public static void convertToC(double value, String from) {
        double result;
        switch (from) {
            case "f" -> result = (value - 32) / 1.8;
            case "k" -> result = value - 273.15;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to celsius yielded %.4f°C%n", value, from, result);
    }
    public static void convertToF(double value, String from) {
        double result;
        switch (from) {
            case "c" -> result = (value * 1.8) + 32;
            case "k" -> result = ((value - 273.15)  * 1.8) + 32;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to celsius yielded %.4f°C%n", value, from, result);
    }
    public static void convertToK(double value, String from) {
        double result;
        switch (from) {
            case "c" -> result = value + 273.15;
            case "f" -> result = ((value - 32) / 1.8) + 273.15;
            default -> throw new IllegalArgumentException("Unknown convert type: " + from); // Should be unreacheable
        };
        printf("Conversion of %s%s to celsius yielded %.4f°C%n", value, from, result);
    }




    public static void print(Object message) {
        System.out.println(message);
    }
    public static void printf(String message, Object... args) {
        System.out.printf(message, args);
    }
    public static String input(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
