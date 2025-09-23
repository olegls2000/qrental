package ee.qrent.billing.transaction.api.in.query.filter;

import java.time.LocalDate;
import java.util.Set;

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
public class DriverAndPeriodAndTypeCodesFilter {
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate datEnd;
  private Set<String> transactionTypeCodes;
}
