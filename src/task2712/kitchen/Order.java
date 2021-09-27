package task2712.kitchen;

import task2712.ConsoleHelper;
import task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalCookingTime () {
        int total = 0;
        for (Dish dish : dishes) {
            total += dish.getDuration();
        }
        return total;
    }
    public boolean isEmpty() {
        if (dishes.isEmpty())
            return true;
        else return false;
    }
    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        if (dishes == null) return "";
        else return "Your order:" + dishes.toString()
                + " of " + tablet
                + ", cooking time " + getTotalCookingTime()
                + "min";

    }


}
