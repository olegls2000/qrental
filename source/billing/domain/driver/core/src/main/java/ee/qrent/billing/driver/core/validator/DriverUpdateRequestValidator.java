package ee.qrent.billing.driver.core.validator;

import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.driver.domain.LegalEntityType;

import java.time.LocalDate;
import java.util.Objects;

public class DriverUpdateRequestValidator extends AbstractDriverRequestValidator
    implements UpdateRequestValidator<DriverUpdateRequest> {

  private final GetQWeekQuery qWeekQuery;

  public DriverUpdateRequestValidator(
      final AttributeChecker attributeChecker,
      final DriverLoadPort loadPort,
      final GetQWeekQuery qWeekQuery,
      final QDateTime qDateTime) {
    super(loadPort, attributeChecker, qDateTime);
    this.qWeekQuery = qWeekQuery;
  }

  @Override
  public ViolationsCollector validate(final DriverUpdateRequest request) {
    final var violationsCollector = getViolationCollector();
    final var driverFromDB = getLoadPort().loadById(request.getId());

    checkObligationNumber(
        request.getHasRequiredObligation(), request.getRequiredObligation(), violationsCollector);
    checkFirstName(request.getFirstName(), violationsCollector);
    checkLastName(request.getLastName(), violationsCollector);
    validateTaxNumber(driverFromDB.getTaxNumber(), request.getTaxNumber(), violationsCollector);
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
    checkRecommendation(request, violationsCollector);

    return violationsCollector;
  }

  private void validateTaxNumber(
      final Long taxNumberFromDb,
      final Long taxNumberFromRequest,
      final ViolationsCollector violationsCollector) {
    checkTaxNumber(taxNumberFromRequest, violationsCollector);

    if (taxNumberFromDb.equals(taxNumberFromRequest)) {
      return;
    }
    checkTaxNumberUniqueness(taxNumberFromRequest, violationsCollector);
  }

  private void checkRecommendation(
      final DriverUpdateRequest request, final ViolationsCollector violationsCollector) {
    final var driverFromDb = getLoadPort().loadById(request.getId());
    final var isRecommendationUpdated = isRecommendationUpdated(request, driverFromDb);
    if (isRecommendationUpdated) {
      final var latestDateForUpdate = getLastDateForUpdate(driverFromDb.getCreatedDate());
      if (latestDateForUpdate.isBefore(getQDateTime().getToday())) {
        violationsCollector.collect(
            format(
                "Driver's recommendation can not be changes after creation week is passed. Last date for update was: "
                    + latestDateForUpdate));
      }
    }
  }

  private LocalDate getLastDateForUpdate(final LocalDate driverCreationDate) {
    final var weekNumber = getWeekNumber(driverCreationDate);
    final var year = driverCreationDate.getYear();
    final var qWeek = qWeekQuery.getByYearAndNumber(year, weekNumber);

    return qWeek.getEnd();
  }

  private boolean isRecommendationUpdated(final DriverUpdateRequest request, final Driver fromDb) {
    final var recommendedByFromRequest = request.getRecommendedByDriverId();
    final var friendshipFromDb = fromDb.getFriendship();
    final var recommendedByFromDb =
        friendshipFromDb == null ? null : friendshipFromDb.getDriverId();

    return !Objects.equals(recommendedByFromRequest, recommendedByFromDb);
  }
}
