package task2712.ad;



import task2712.ConsoleHelper;
import task2712.statistic.StatisticManager;
import task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementManager<getActualList> {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    private List<Advertisement> bestList = new ArrayList<>();
    private long maxAmount = 0L;
    private int maxDuration = 0;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }


    public void processVideos() {

        provideBestSet(new ArrayList<>(), maxDuration, maxAmount);
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestList, maxAmount, maxDuration));
        displayAdvertisement();

    }

    public void provideBestSet(ArrayList<Advertisement> listIn, int duration, long amount) {
        if (duration > timeSeconds)
            return;
        else if (amount > maxAmount
                || amount == maxAmount && (duration > maxDuration
                || duration == maxDuration && listIn.size() < bestList.size())) {
            bestList = listIn;
            maxAmount = amount;
            maxDuration = duration;
            if (duration == timeSeconds)
                return;
        }
        ArrayList<Advertisement> tmp = getActualList();
        tmp.removeAll(listIn);
        for (Advertisement ad : tmp) {
            if (ad.getHits() <= 0) continue;
            ArrayList<Advertisement> currentList = new ArrayList<>(listIn);
            currentList.add(ad);
            provideBestSet(currentList, duration + ad.getDuration(), amount + ad.getAmountPerOneDisplaying());
        }
    }

    private ArrayList<Advertisement> getActualList() {
        ArrayList<Advertisement> newList = new ArrayList<>();
        for (Advertisement ad : storage.list()) {
            if (ad.getHits() > 0) {
                newList.add(ad);
            }
        }
        return newList;
    }
    private void displayAdvertisement() {
        if (bestList == null || bestList.isEmpty()) {
            throw new NoVideoAvailableException();
        }
        // bestList.stream().sorted(
        //        Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying).reversed()
        //                .thenComparingLong(a -> a.getAmountPerOneDisplaying() / a.getDuration()));
        bestList.sort((o1, o2) -> {
            long l = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
            return (int) (l != 0 ? l : o2.getDuration() - o1.getDuration());
        });
        for (Advertisement adv : bestList) {
            ConsoleHelper.writeMessage(adv.getName() + " is displaying... " + adv.getAmountPerOneDisplaying() +
                    ", " + (1000 * adv.getAmountPerOneDisplaying() / adv.getDuration()));
            adv.revalidate();
        }
    }
 /*       optimalVideoSet.sort((o1, o2) -> {
            long l = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
            return (int) (l != 0 ? l : o2.getDuration() - o1.getDuration());
        });

        for (Advertisement ad : optimalVideoSet) {
            displayInPlayer(ad);
            ad.revalidate();
        }
 */
}