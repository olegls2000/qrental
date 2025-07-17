package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.driver.api.in.request.CallSignAddRequest;

public class CallSignHelper {

  public static CallSignAddRequest getValidAddRequest() {
    final var request = new CallSignAddRequest();
    request.setCallSign(1);
    request.setComment("it_comment_1");

    return request;
  }
}
