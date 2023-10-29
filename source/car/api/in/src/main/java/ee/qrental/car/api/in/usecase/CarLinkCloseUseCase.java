package ee.qrental.car.api.in.usecase;

import ee.qrental.car.api.in.request.CarLinkCloseRequest;

public interface CarLinkCloseUseCase {

  void close(final CarLinkCloseRequest closeRequest);
}
