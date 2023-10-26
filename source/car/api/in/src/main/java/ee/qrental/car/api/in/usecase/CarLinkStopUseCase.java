package ee.qrental.car.api.in.usecase;

import ee.qrental.car.api.in.request.CarLinkStopRequest;

public interface CarLinkStopUseCase {

  void stop(final CarLinkStopRequest stopRequest);
}
