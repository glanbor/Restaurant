package task2712;

import task2712.kitchen.Cook;
import task2712.kitchen.Order;
import task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.setQueue(ORDER_QUEUE);
            tablets.add(tablet);
        }
        Cook cook = new Cook("Amigo");
        cook.setQueue(ORDER_QUEUE);
        Cook cook2 = new Cook("Figo");
        cook2.setQueue(ORDER_QUEUE);
        new Thread(cook).start();
        new Thread(cook2).start();

        Waiter waiter = new Waiter();

        cook.addObserver(waiter);
        cook2.addObserver(waiter);

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException ignore) {
        }


        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
