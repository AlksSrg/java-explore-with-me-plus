import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import temp.EndpointHit;
import temp.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Component
public class StatsClientImp implements StatsClient {
    private final RestClient restClient;
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClientImp(@Value("${stats-client.url:http://localhost:9090}") String uriBase) {
        this.restClient =  RestClient.builder()
                .baseUrl(uriBase)
                .build();
    }

    @Override
    public boolean saveStat(EndpointHit hintObj) {
        // TODO: наверное нужно навешать проверок на корректность данных
        // TODO: как обработать ошибки?
        try {
            restClient.post()
                    .uri( "/hint")
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
        // в случае ошибочных параметров будем возвращать пустое значение
        if (start == null || end == null || end.isBefore(start))
            return List.of();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("start", start.format(DATE_TIME_FORMATTER));
        uriVariables.put("end", end.format(DATE_TIME_FORMATTER));
        uriVariables.put("unique", Boolean.toString(unique));
        if (uris != null)
            uriVariables.put("uris", uris.toString());

        try {
            return restClient.get()
                    .uri("/stats", uriVariables)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
         // не доступен
        } catch (ResourceAccessException ex) {
            return List.of();
        }
    }
}
