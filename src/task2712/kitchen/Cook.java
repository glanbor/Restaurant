package task2712.kitchen;


import task2712.ConsoleHelper;
import task2712.statistic.StatisticManager;
import task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    String name;
    private boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CookedOrderEventDataRow cookedOrderEventDataRow = new CookedOrderEventDataRow(order.toString(), name, 60 * order.getTotalCookingTime(), order.getDishes());
        StatisticManager.getInstance().register(cookedOrderEventDataRow);
        ConsoleHelper.writeMessage("Start cooking - " + order);
        setChanged();
        notifyObservers(order);
        busy = false;
    }

    @Override
    public void run() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                        if (!queue.isEmpty()) {
                            if (!Cook.this.isBusy()) {
                                Cook.this.startCookingOrder(queue.take());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
