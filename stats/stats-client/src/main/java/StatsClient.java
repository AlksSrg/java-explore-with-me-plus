import temp.EndpointHit;
import temp.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {
    boolean saveStat(EndpointHit hint);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, boolean unique);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
