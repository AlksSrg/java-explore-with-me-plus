import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import temp.EndpointHit;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
                classes = {StatsClientImp.class})
class StatsClientImpTest {
    private final StatsClient statsClient;

    @Test
    public void testMethodSaveStat() {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp("ewm-main-service");
        endpointHit.setUri("/events");
        endpointHit.setApi("127.0.0.1");
        endpointHit.setTimestamp(LocalDateTime.now());

        statsClient.saveStat(endpointHit);
    }

    @Test
    public void testMethodGetStats() {
        statsClient.getStats(LocalDateTime.now(), LocalDateTime.now().plusDays(5), true);
    }

}