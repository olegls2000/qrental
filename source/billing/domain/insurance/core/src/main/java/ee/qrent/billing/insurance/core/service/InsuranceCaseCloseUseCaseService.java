package ee.qrent.billing.insurance.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;
import static java.math.BigDecimal.ROUND_HALF_UP;

import ee.qrent.billing.insurance.api.in.request.InsuranceCaseCloseRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCasePreCloseResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseCloseUseCase;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class InsuranceCaseCloseUseCaseService implements InsuranceCaseCloseUseCase {

  private final BigDecimal SELF_RESPONSIBILITY = BigDecimal.valueOf(500L);
  private final InsuranceCaseUpdatePort updatePort;
  private final InsuranceCaseLoadPort loadPort;
  private final CloseRequestValidator<InsuranceCaseCloseRequest> closeRequestValidator;
  private final GetQKaskoQuery getQKaskoQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final QDateTime qDateTime;
  private final TransactionAddUseCase transactionAddUseCase;

  public InsuranceCasePreCloseResponse getPreCloseResponse(final Long insuranceCaseId) {
    final var toCloseInsuranceCase = loadPort.loadById(insuranceCaseId);
    final var driverId = toCloseInsuranceCase.getDriverId();
    final var driverInfo = driverQuery.getObjectInfo(driverId);
    final var qWeekId = qWeekQuery.getCurrentWeek().getId();
    final var withQKasko = getQKaskoQuery.hasQKasko(driverId, qWeekId);
    final var amountToPayAdjustedByQKasko =
        getAmountToPayAdjustedByQKasko(toCloseInsuranceCase, withQKasko);
    final var paidAmount = getAmountPaid(toCloseInsuranceCase);
    final var paymentAmount = amountToPayAdjustedByQKasko.subtract(paidAmount);

    return InsuranceCasePreCloseResponse.builder()
        .insuranceCaseId(insuranceCaseId)
        .driverId(driverId)
        .driverInfo(driverInfo)
        .withQKasko(withQKasko)
        .paymentAmount(paymentAmount)
        .paidAmount(paidAmount)
        .originalAmount(toCloseInsuranceCase.getDamageAmount())
        .build();
  }

  @Override
  public List<InsuranceCasePreCloseResponse> getPreCloseResponses(
      final List<Long> insuranceCaseIds) {
    return insuranceCaseIds.stream().map(this::getPreCloseResponse).toList();
  }

  @Override
  public void close(final InsuranceCaseCloseRequest request) {
    final var violationsCollector = closeRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var insuranceCaseId = request.getId();
    final var preCloseResponse = getPreCloseResponse(insuranceCaseId);
    final var damageTransaction = getDamageTransaction(preCloseResponse);
    transactionAddUseCase.add(damageTransaction);
    final var toCloseInsuranceCase = loadPort.loadById(insuranceCaseId);
    toCloseInsuranceCase.setActive(false);
    updatePort.update(toCloseInsuranceCase);
  }

  private TransactionAddRequest getDamageTransaction(
      final InsuranceCasePreCloseResponse preCloseResponse) {
    final var transaction = new TransactionAddRequest();
    transaction.setAmount(preCloseResponse.getPaymentAmount());
    transaction.setDriverId(preCloseResponse.getDriverId());
    transaction.setDate(qDateTime.getToday());
    final var transactionTypeNameForDamage = "damage payment";
    transaction.setTransactionTypeId(getTransactionTypeIdByName(transactionTypeNameForDamage));
    transaction.setComment(
        "Close payment for insurance case with id: " + preCloseResponse.getInsuranceCaseId());
    return transaction;
  }

  private BigDecimal getAmountPaid(final InsuranceCase toCloseInsuranceCase) {
    return transactionQuery.getAllByInsuranceCaseId(toCloseInsuranceCase.getId()).stream()
        .map(TransactionResponse::getRealAmount)
        .map(BigDecimal::abs)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal getAmountToPayAdjustedByQKasko(
      final InsuranceCase insuranceCase, final boolean withQKasko) {
    final var damage = insuranceCase.getDamageAmount();
    if (damage.compareTo(SELF_RESPONSIBILITY) <= 0) {
      return damage;
    }

    if (!withQKasko) {
      return damage;
    }

    final var damageWithoutSelfResponsibility = damage.subtract(SELF_RESPONSIBILITY);
    return damageWithoutSelfResponsibility.divide(new BigDecimal(2), 2, ROUND_HALF_UP);
  }

  private Long getTransactionTypeIdByName(final String transactionTypeName) {
    final var transactionType = transactionTypeQuery.getByName(transactionTypeName);
    if (transactionType == null) {
      throw new RuntimeException(
          format(
              "Transaction Type with name: %s, does not exist. "
                  + "Please create it, before Insurance Case Damage calculation",
              transactionTypeName));
    }
    return transactionType.getId();
  }
}
