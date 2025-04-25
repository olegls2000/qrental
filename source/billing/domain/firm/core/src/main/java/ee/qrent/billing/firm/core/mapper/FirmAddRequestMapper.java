package ee.qrent.billing.firm.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.firm.api.in.request.FirmAddRequest;
import ee.qrent.billing.firm.domain.Firm;

public class FirmAddRequestMapper implements AddRequestMapper<FirmAddRequest, Firm> {

  @Override
  public Firm toDomain(FirmAddRequest request) {
    return Firm.builder()
        .id(null)
        .name(request.getName())
        .ceoFirstName(request.getCeoFirstName())
        .ceoLastName(request.getCeoLastName())
        .ceoDeputy1FirstName(request.getCeoDeputy1FirstName())
        .ceoDeputy1LastName(request.getCeoDeputy1LastName())
        .ceoDeputy2FirstName(request.getCeoDeputy2FirstName())
        .ceoDeputy2LastName(request.getCeoDeputy2LastName())
        .ceoDeputy3FirstName(request.getCeoDeputy3FirstName())
        .ceoDeputy3LastName(request.getCeoDeputy3LastName())
        .iban(request.getIban())
        .registrationNumber(request.getRegistrationNumber())
        .vatNumber(request.getVatNumber())
        .email(request.getEmail())
        .postAddress(request.getPostAddress())
        .phone(request.getPhone())
        .bank(request.getBank())
        .qGroup(request.getQGroup())
        .comment(request.getComment())
        .build();
  }
}
