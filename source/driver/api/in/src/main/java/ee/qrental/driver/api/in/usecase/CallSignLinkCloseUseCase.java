package ee.qrental.driver.api.in.usecase;

import ee.qrental.driver.api.in.request.CallSignLinkCloseRequest;

public interface CallSignLinkCloseUseCase {
  void close(final CallSignLinkCloseRequest request);
}
