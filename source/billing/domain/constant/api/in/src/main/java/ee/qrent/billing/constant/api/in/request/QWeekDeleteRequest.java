package ee.qrent.billing.constant.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QWeekDeleteRequest extends AbstractDeleteRequest {
  public QWeekDeleteRequest(final Long id) {
    super(id);
  }
}
