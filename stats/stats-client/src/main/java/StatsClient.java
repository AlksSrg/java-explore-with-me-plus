import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class StatsClient {
    private final RestClient restClient;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClient(@Value("${stats-client.url:http://localhost:9090}") String uriBase) {
        this.restClient = RestClient.builder()
                .baseUrl(uriBase)
                .build();
    }

    /* так как сервер статистики у нас второстепенен то результат работы
       только информационный, без исключений
     */
    public boolean saveStat(String app, String uri, String ip) {
        if (app == null || app.isBlank()
                || uri == null || uri.isBlank()
                || ip == null || ip.isBlank()) {
            log.error("Некорректные входные параметры: app - {}, uri - {}, api - {}", app, uri, ip);
            return false;
        }

        EndpointHitDto endpointHit = EndpointHitDto.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();

        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri("/hit")
                    .contentType(APPLICATION_JSON)
                    .body(endpointHit)
                    .retrieve()
                    .toBodilessEntity();
            return response.getStatusCode() == HttpStatus.CREATED;
        } catch (ResourceAccessException ex) {
            log.error("Сервер не доступен");
            return false;  // лишнее, но, что то нужно сделать
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, boolean unique) {
        return getStats(start, end, null, unique);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start == null || end == null || end.isBefore(start)) {
            log.error("Дата окнчания раньше даты начала");
            return List.of();
        }

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
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (ResourceAccessException ex) {
            log.error("Сервер не доступен");
            return List.of();
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            return List.of();
        }
    }
}
