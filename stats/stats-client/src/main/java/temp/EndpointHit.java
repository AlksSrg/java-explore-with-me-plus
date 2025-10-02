package temp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndpointHit {
    private String app;
    private String uri;
    private String api;
    private LocalDateTime timestamp;
}
