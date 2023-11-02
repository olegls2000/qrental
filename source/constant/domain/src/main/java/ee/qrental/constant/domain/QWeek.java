package ee.qrental.constant.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class QWeek {
    private Long id;
    private Integer year;
    private Integer number;
    private String description;
    private LocalDate start;
    private LocalDate end;
}
