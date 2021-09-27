package task2712.statistic;

import task2712.statistic.event.CookedOrderEventDataRow;
import task2712.statistic.event.EventDataRow;
import task2712.statistic.event.EventType;
import task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {
    }
    public Map<String, Long> getProfitOfAdvertisment() {
        Map<String, Long> report = new TreeMap<>(Collections.reverseOrder());
        List <EventDataRow> listOfVideos = statisticStorage.get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (EventDataRow eventDataRows : listOfVideos) {
            VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) eventDataRows;
            String date = simpleDateFormat.format(videoSelectedEventDataRow.getDate());
            if (!report.containsKey(date)) {
                report.put(date, videoSelectedEventDataRow.getAmount());
            }
            else {
                long value = report.get(date);
                value += videoSelectedEventDataRow.getAmount();
                report.put(date, value);
            }
        }
        return report;
    }
    public Map<String, Map<String, Integer>> getCookWorkloadingMap() {
        Map<String, Map<String, Integer>> res = new HashMap(); //name, time
        List<EventDataRow> rows = statisticStorage.get(EventType.COOKED_ORDER);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (EventDataRow row : rows) {
            CookedOrderEventDataRow dataRow = (CookedOrderEventDataRow) row;
            String date = format.format(dataRow.getDate());
            if (!res.containsKey(date)) {
                res.put(date, new HashMap<String, Integer>());
            }
            Map<String, Integer> cookMap = res.get(date);
            String cookName = dataRow.getCookName();
            if (!cookMap.containsKey(cookName)) {
                cookMap.put(cookName, 0);
            }

            Integer totalTime = cookMap.get(cookName);
            cookMap.put(cookName, totalTime + dataRow.getTime());
        }

        return res;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private List<EventDataRow> get(EventType eventType) {
            if (!this.storage.containsKey(eventType))
                throw new UnsupportedOperationException();
            return this.storage.get(eventType);
        }

        private StatisticStorage() {
            for (EventType type : EventType.values()) {
                this.storage.put(type, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            EventType type = data.getType();
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            this.storage.get(type).add(data);
        }
    }

    public void register(EventDataRow data) {
        this.statisticStorage.put(data);
    }

}
