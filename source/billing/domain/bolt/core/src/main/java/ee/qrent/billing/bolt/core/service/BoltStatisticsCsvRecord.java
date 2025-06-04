package ee.qrent.billing.bolt.core.service;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoltStatisticsCsvRecord {
  @CsvBindByName(column = "Individual identifier")
  private String individualId;

  @CsvBindByName(column = "Finished orders")
  private Integer finishedOrders;
}
