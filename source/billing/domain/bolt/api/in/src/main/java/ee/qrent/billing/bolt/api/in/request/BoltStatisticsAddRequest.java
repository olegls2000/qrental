package ee.qrent.billing.bolt.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoltStatisticsAddRequest extends AbstractAddRequest {
  private byte[] data;
  private String region;
  private String fileName;
  private Integer month;
  private Integer year;
}
