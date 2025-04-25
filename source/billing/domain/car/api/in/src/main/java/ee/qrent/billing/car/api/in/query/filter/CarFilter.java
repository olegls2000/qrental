package ee.qrent.billing.car.api.in.query.filter;

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
public class CarFilter {
    private Availability state = Availability.ALL;
}
