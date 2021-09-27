package task2712.kitchen;

import java.util.StringJoiner;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);
    private int duration;

    Dish(int duration) {
       this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Dish dish : Dish.values()) {
            stringJoiner.add(dish.toString());
        }
        return stringJoiner.toString();
    }
}
