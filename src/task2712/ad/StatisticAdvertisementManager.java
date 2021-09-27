package task2712.ad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatisticAdvertisementManager {
    private static final StatisticAdvertisementManager INSTANCE = new StatisticAdvertisementManager();
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {  }

    public static StatisticAdvertisementManager getInstance() {
        return INSTANCE;
    }
    public List<Advertisement> getActiveAd (boolean isActiveNeeded) {
        List<Advertisement> activeVideos = new ArrayList<>();
        List<Advertisement> archivedVideos = new ArrayList<>();
        for (Advertisement video : advertisementStorage.list()) {
            if (video.isActive())
                activeVideos.add(video);
            else archivedVideos.add(video);
        }
        activeVideos.stream().sorted(Comparator.comparing(ad -> ad.getName().toLowerCase()));

        return isActiveNeeded ? activeVideos : archivedVideos;
    }

}

