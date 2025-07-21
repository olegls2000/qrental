package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.firm.api.in.request.FirmAddRequest;

public class FirmHelper {

  public static FirmAddRequest getValidAddRequest() {
    final var request = new FirmAddRequest();
    request.setName("it_name");
    request.setCeoFirstName("it_ceo_first_name");
    request.setCeoLastName("it_ceo_last_name");
    request.setCeoDeputy1FirstName("it_deputy_1_first_name");
    request.setCeoDeputy1LastName("it_deputy_1_last_name");
    request.setCeoDeputy2FirstName("it_deputy_2_first_name");
    request.setCeoDeputy2LastName("it_deputy_2_last_name");
    request.setCeoDeputy3FirstName("it_deputy_3_first_name");
    request.setCeoDeputy3LastName("it_deputy_3_last_name");
    request.setIban("it_iban");
    request.setRegistrationNumber("it_registration_number");
    request.setVatNumber("it_vat_number");
    request.setEmail("it_email@mail.com");
    request.setPostAddress("it_post_address");
    request.setPhone("it_phone");
    request.setBank("it_bank");
    request.setQGroup(true);
    request.setComment("it_comment");

    return request;
  }
}
