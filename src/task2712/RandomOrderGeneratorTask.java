package task2712;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int randomNumber = (int) (Math.random() * tablets.size());
                Tablet randomTablet = tablets.get(randomNumber);
                randomTablet.createTestOrder();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {

        }
    }
}
