package ee.qrent.billing.report.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO make abstraction for calculation
@SuperBuilder
@Getter
@Setter
public class WeeklyReportCalculation {
  private Long id;
  private Long qWeekId;
  private LocalDate actionDate;
  private List<WeeklyReportTransactionsLink> reportTransactionLinks =  new ArrayList<>();
  private String comment;
}
