package ee.qrent.billing.driver.api.in.usecase;

import ee.qrent.billing.driver.api.in.request.CallSignLinkCloseRequest;

public interface CallSignLinkCloseUseCase {
  void close(final CallSignLinkCloseRequest request);
}
