import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import temp.EndpointHit;
import temp.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Component
public class StatsClientImp implements StatsClient {
    @Value("${stats-client.url}")
    private String uriBase;
    private RestClient restClient = RestClient.create();

    @Override
    public boolean saveStat(EndpointHit hintObj) {
        // TODO: наверное нужно навешать проверок на корректность данных
        // TODO: как обработать ошибки?
        try {
            restClient.post()
                    .uri(uriBase + "/hit")
                    .contentType(APPLICATION_JSON)
                    .body(hintObj)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        // не доступен
        } catch (ResourceAccessException ex) {
            return false;  // лишнее, но, что то нужно сделать(ошибку не должны по ТЗ выбрасывать)
        }
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, boolean unique) {
        return getStats(start, end, null, unique);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return List.of();
    }
}
