package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.firm.api.in.request.FirmAddRequest;

public class FirmHelper {

  public static FirmAddRequest getValidAddRequest() {
    final var request = new FirmAddRequest();



    return request;
  }
}
