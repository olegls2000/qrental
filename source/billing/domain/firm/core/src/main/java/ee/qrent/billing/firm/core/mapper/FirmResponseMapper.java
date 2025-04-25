package ee.qrent.billing.firm.core.mapper;


import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.firm.api.in.response.FirmResponse;
import ee.qrent.billing.firm.domain.Firm;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class FirmResponseMapper implements ResponseMapper<FirmResponse, Firm> {
  @Override
  public FirmResponse toResponse(final Firm domain) {
    return FirmResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .ceoName(format("%s %s", domain.getCeoFirstName(), domain.getCeoLastName()))
        .deputies(getDeputiesNames(domain))
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

  private List<String> getDeputiesNames(final Firm domain) {
    final var result = new ArrayList<String>(3);
    final var deputy1FirstName = domain.getCeoDeputy1FirstName();
    final var deputy1LastName = domain.getCeoDeputy1LastName();
    if (deputy1LastName != null) {
      result.add(format("%s %s", deputy1FirstName, deputy1LastName));
    }
    final var deputy2FirstName = domain.getCeoDeputy2FirstName();
    final var deputy2LastName = domain.getCeoDeputy2LastName();
    if (deputy2LastName != null) {
      result.add(format("%s %s", deputy2FirstName, deputy2LastName));
    }
    final var deputy3FirstName = domain.getCeoDeputy3FirstName();
    final var deputy3LastName = domain.getCeoDeputy3LastName();
    if (deputy3LastName != null) {
      result.add(format("%s %s", deputy3FirstName, deputy3LastName));
    }

    return result;
  }

  @Override
  public String toObjectInfo(Firm domain) {
    return format("%s %s", domain.getName(), domain.getRegistrationNumber());
  }
}
