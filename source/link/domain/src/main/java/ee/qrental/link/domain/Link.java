package ee.qrental.link.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class Link {
    private Long id;
    private Long carId;
    private Long driverId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String comment;
}
