package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;

public class QWeekHelper {

  public static QWeekAddRequest getValidAddRequest() {
    final var validAddRequest = new QWeekAddRequest();

    return validAddRequest;
  }
}
