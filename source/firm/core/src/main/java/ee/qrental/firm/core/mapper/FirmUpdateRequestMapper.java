package ee.qrental.firm.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.firm.api.in.request.FirmUpdateRequest;
import ee.qrental.firm.domain.Firm;

public class FirmUpdateRequestMapper implements UpdateRequestMapper<FirmUpdateRequest, Firm> {

  @Override
  public Firm toDomain(final FirmUpdateRequest request) {
    return Firm.builder()
            .id(request.getId())
            .firmName(request.getFirmName())
            .iban(request.getIban())
            .regNumber(request.getRegNumber())
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
            .firmName(domain.getFirmName())
            .iban(domain.getIban())
            .regNumber(domain.getRegNumber())
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
