package ee.qrent.billing.car.api.in.usecase;

import ee.qrent.billing.car.api.in.request.CarLinkCloseRequest;

public interface CarLinkCloseUseCase {

  void close(final CarLinkCloseRequest closeRequest);
}
