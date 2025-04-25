package ee.qrent.billing.firm.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.firm.api.in.request.FirmUpdateRequest;
import ee.qrent.billing.firm.domain.Firm;

public class FirmUpdateRequestMapper implements UpdateRequestMapper<FirmUpdateRequest, Firm> {

  @Override
  public Firm toDomain(final FirmUpdateRequest request) {
    return Firm.builder()
        .id(request.getId())
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
        .comment(request.getComment())
        .email(request.getEmail())
        .postAddress(request.getPostAddress())
        .phone(request.getPhone())
        .bank(request.getBank())
        .qGroup(request.getQGroup())
        .build();
  }

  @Override
  public FirmUpdateRequest toRequest(final Firm domain) {
    return FirmUpdateRequest.builder()
        .id(domain.getId())
        .name(domain.getName())
        .ceoFirstName(domain.getCeoFirstName())
        .ceoLastName(domain.getCeoLastName())
        .ceoDeputy1FirstName(domain.getCeoDeputy1FirstName())
        .ceoDeputy1LastName(domain.getCeoDeputy1LastName())
        .ceoDeputy2FirstName(domain.getCeoDeputy2FirstName())
        .ceoDeputy2LastName(domain.getCeoDeputy2LastName())
        .ceoDeputy3FirstName(domain.getCeoDeputy3FirstName())
        .ceoDeputy3LastName(domain.getCeoDeputy3LastName())
        .iban(domain.getIban())
        .registrationNumber(domain.getRegistrationNumber())
        .vatNumber(domain.getVatNumber())
        .comment(domain.getComment())
        .email(domain.getEmail())
        .postAddress(domain.getPostAddress())
        .phone(domain.getPhone())
        .bank(domain.getBank())
        .qGroup(domain.getQGroup())
        .build();
  }
}
