package task2712.kitchen;



import task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<>();
        Dish[] array = Dish.values();
        int numberOfDishes = (int) (Math.random() * 3 + 2);
        for (int i = 0; i < numberOfDishes ; i++) {
            int dishIndex = (int) (Math.random() * array.length);
            dishes.add(array[dishIndex]);
        }
    }
}
