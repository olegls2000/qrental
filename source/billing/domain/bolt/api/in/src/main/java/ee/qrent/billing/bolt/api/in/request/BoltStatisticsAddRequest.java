package ee.qrent.billing.bolt.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@NoArgsConstructor
public class BoltStatisticsAddRequest extends AbstractAddRequest {
  private InputStream inputStream;
  private String region;
  private String fileName;
  private Integer month;
  private Integer year;
}
