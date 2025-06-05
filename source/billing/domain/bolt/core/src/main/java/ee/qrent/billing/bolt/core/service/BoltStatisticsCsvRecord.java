package ee.qrent.billing.bolt.core.service;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoltStatisticsCsvRecord {

  @CsvBindByName(column = "Driver")
  private String driver;

  @CsvBindByName(column = "Individual identifier")
  private String individualId;

  @CsvBindByName(column = "Finished rides")
  private Integer finishedOrders;
}
