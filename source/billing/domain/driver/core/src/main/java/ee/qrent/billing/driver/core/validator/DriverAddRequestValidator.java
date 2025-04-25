package ee.qrent.billing.driver.core.validator;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.domain.LegalEntityType;

public class DriverAddRequestValidator extends AbstractDriverRequestValidator
    implements AddRequestValidator<DriverAddRequest> {

  public DriverAddRequestValidator(
      final AttributeChecker attributeChecker,
      final DriverLoadPort loadPort,
      final QDateTime qDateTime) {
    super(loadPort, attributeChecker, qDateTime);
  }

  @Override
  public ViolationsCollector validate(final DriverAddRequest request) {
    final var violationsCollector = getViolationCollector();
    checkObligationNumber(
        request.getHasRequiredObligation(), request.getRequiredObligation(), violationsCollector);
    checkFirstName(request.getFirstName(), violationsCollector);
    checkLastName(request.getLastName(), violationsCollector);
    validateTaxNumber(request.getTaxNumber(), violationsCollector);
    checkAddress(request.getAddress(), violationsCollector);
    checkLicenseNumber(request.getDriverLicenseNumber(), violationsCollector);
    checkLicenseExpirationDate(request.getDriverLicenseExp(), violationsCollector);
    checkTaxiLicense(request.getTaxiLicense(), violationsCollector);
    checkPhoneNumber(request.getPhone(), violationsCollector);
    checkEmail(request.getEmail(), violationsCollector);
    if (request.getLegalEntityType().equals(LegalEntityType.COMPANY.name())) {
      checkCompanyName(request.getCompanyName(), violationsCollector);
      checkRegistrationNumber(request.getRegNumber(), violationsCollector);
      checkCompanyVat(request.getCompanyVat(), violationsCollector);
      checkCeoFirstName(request.getCompanyCeoFirstName(), violationsCollector);
      checkCeoLastName(request.getCompanyCeoLastName(), violationsCollector);
      checkCeoTaxNumber(request.getCompanyCeoTaxNumber(), violationsCollector);
      checkCompanyAddress(request.getCompanyAddress(), violationsCollector);
    }
    if (request.getLegalEntityType().equals(LegalEntityType.LHV_ACCOUNT.name())) {
      checkLhvAccount(request.getLhvAccount(), violationsCollector);
    }
    checkComment(request.getComment(), violationsCollector);

    return violationsCollector;
  }

  private void validateTaxNumber(
      final Long taxNumber, final ViolationsCollector violationsCollector) {
    checkTaxNumber(taxNumber, violationsCollector);
    checkTaxNumberUniqueness(taxNumber, violationsCollector);
  }
}
