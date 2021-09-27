package task2712;


import task2712.ad.Advertisement;
import task2712.ad.StatisticAdvertisementManager;
import task2712.statistic.StatisticManager;

import java.util.*;

public class DirectorTablet {
    public void printAdvertisementProfit() {
        Map<String, Long> report = StatisticManager.getInstance().getProfitOfAdvertisment();

        long total = 0;
        for (Map.Entry<String, Long> entry : report.entrySet()) {
            ConsoleHelper.writeMessage(entry.getKey() + " - " + 1.0 * entry.getValue() / 100);
            total += entry.getValue();
        }
        ConsoleHelper.writeMessage("Total - " + 1.0 * total / 100);
    }

    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<String, Map<String, Integer>> cookWorkloadingMap = statisticManager.getCookWorkloadingMap();
        ArrayList<String> list = new ArrayList(cookWorkloadingMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            Map<String, Integer> cookMap = cookWorkloadingMap.get(key);
            System.out.println(key);

            ArrayList<String> cookNames = new ArrayList(cookMap.keySet());
            Collections.sort(cookNames);
            for (String cookName : cookNames) {
                System.out.println(cookName + " - " + ((cookMap.get(cookName) + 59) / 60) + " min");
            }

            System.out.println();
        }
    }

    public void printActiveVideoSet() {

        for (Advertisement ad : StatisticAdvertisementManager.getInstance().getActiveAd(true)
        ) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> videoSet = StatisticAdvertisementManager.getInstance().getActiveAd(false);
        Collections.sort(videoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });

        for (Advertisement advertisement : videoSet) {
            System.out.println(advertisement.getName());
        }
    }

}
