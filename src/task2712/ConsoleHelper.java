package task2712;

import task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishesForOrder = new ArrayList<>();
        ConsoleHelper.writeMessage("Please choose a dish from the list:" + Dish.allDishesToString() + "\n or type 'exit' to complete the order");
        while (true) {
            String order = readString().trim();
            if ("exit".equalsIgnoreCase(order))
                break;

            try {
                Dish dish = Dish.valueOf(order.toUpperCase());
                dishesForOrder.add(dish);
                writeMessage(dish + " added to your order");
            } catch (IllegalArgumentException e) {
                writeMessage(order + " is not in menu");
            }
        }
        return dishesForOrder;
    }
}
