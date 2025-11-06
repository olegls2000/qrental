package ee.qrent.billing.bolt.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoltStatisticsDeleteRequest extends AbstractDeleteRequest {
  public BoltStatisticsDeleteRequest(final Long id) {
    super(id);
  }
}
