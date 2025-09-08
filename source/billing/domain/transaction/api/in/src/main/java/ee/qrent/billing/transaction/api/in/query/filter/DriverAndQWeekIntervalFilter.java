package ee.qrent.billing.transaction.api.in.query.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@ToString
public class DriverAndQWeekIntervalFilter {
    private Long startQWeekId;
    private Long endQWeekId;
    private Long driverId;
}
