package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.driver.api.in.request.CallSignAddRequest;

public class CallSignHelper {

  public static CallSignAddRequest getValidAddRequest() {

    return getCallSignAddRequest(1);
  }

  public static CallSignAddRequest getValidAddRequest(final Integer callSign) {

    return getCallSignAddRequest(callSign);
  }

  private static CallSignAddRequest getCallSignAddRequest(final Integer callSign) {
    final var request = new CallSignAddRequest();
    request.setCallSign(callSign);
    request.setComment("it_comment_1");

    return request;
  }
}
