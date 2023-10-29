package ee.qrental.driver.api.in.usecase;

import ee.qrental.driver.api.in.request.CallSignLinkStopRequest;

public interface CallSignLinkStopUseCase {
  void close(final CallSignLinkStopRequest request);
}
