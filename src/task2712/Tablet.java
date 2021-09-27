package task2712;

import task2712.ad.AdvertisementManager;
import task2712.ad.NoVideoAvailableException;
import task2712.kitchen.Order;
import task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number) {
        this.number = number;
    }
    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        return order;
    }
    public void createTestOrder() {
        TestOrder order = null;
        try {
            order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    private boolean processOrder (Order order) {
        ConsoleHelper.writeMessage(order.toString());
        if (order.isEmpty()) {
            return true;
        }
        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        advertisementManager.processVideos();
        try {
            queue.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
