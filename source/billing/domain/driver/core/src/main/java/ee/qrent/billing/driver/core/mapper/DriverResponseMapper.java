package ee.qrent.billing.driver.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.driver.domain.CallSign;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverResponseMapper implements ResponseMapper<DriverResponse, Driver> {
  private final GetFirmQuery firmQuery;
  private final GetQKaskoQuery qKaskoQuery;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public DriverResponse toResponse(final Driver domain) {
    final var qFirmId = domain.getQFirmId();
    final var qFirmName = qFirmId == null ? null : firmQuery.getById(qFirmId).getName();
    final var callSign = getCallSign(domain.getCallSign());

    return DriverResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .callSign(callSign)
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getTaxNumber())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .address(domain.getAddress())
        .legalEntityType(domain.getLegalEntityType().getLabel())
        .lhvAccount(domain.getLhvAccount())
        .companyName(domain.getCompanyName())
        .companyCeoName(
            format("%s %s", domain.getCompanyCeoFirstName(), domain.getCompanyCeoLastName()))
        .companyCeoTaxNumber(domain.getCompanyCeoTaxNumber())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .companyVat(domain.getCompanyVat())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .needInvoicesByEmail(domain.getNeedInvoicesByEmail())
        .needFee(domain.getNeedFee())
        .byTelegram(domain.getByTelegram())
        .byWhatsApp(domain.getByWhatsApp())
        .byViber(domain.getByViber())
        .byEmail(domain.getByEmail())
        .bySms(domain.getBySms())
        .byPhone(domain.getByPhone())
        .deposit(domain.getDeposit())
        .hasRequiredObligation(domain.hasRequiredObligation())
        .requiredObligation(domain.getRequiredObligation())
        .qFirmId(qFirmId)
        .qFirmName(qFirmName)
        .hasQKasko(hasQKasko(domain.getId()))
        .comment(domain.getComment())
        .createdDate(domain.getCreatedDate())
        .build();
  }

  private Boolean hasQKasko(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();
    if (currentQWeek == null) {
      throw new RuntimeException("Current week is null, please create a QWeek for today");
    }
    return qKaskoQuery.hasQKasko(driverId, currentQWeek.getId());
  }

  private Integer getCallSign(final CallSign callSign) {
    if (callSign == null) {
      return null;
    }
    return callSign.getCallSign();
  }

  @Override
  public String toObjectInfo(Driver domain) {
    return format("%s %s ", domain.getFirstName(), domain.getLastName());
  }
}
