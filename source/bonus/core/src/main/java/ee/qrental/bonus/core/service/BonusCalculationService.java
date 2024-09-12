package ee.qrental.bonus.core.service;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrental.bonus.api.out.*;
import ee.qrental.bonus.core.mapper.BonusCalculationAddRequestMapper;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.bonus.domain.BonusCalculationResult;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import ee.qrental.user.api.in.response.UserAccountResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationService implements BonusCalculationAddUseCase {
  private final GetQWeekQuery qWeekQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final BonusProgramLoadPort bonusProgramLoadPort;
  private final GetUserAccountQuery userAccountQuery;
  private final EmailSendUseCase emailSendUseCase;
  private final BonusCalculationAddPort calculationAddPort;
  private final ObligationLoadPort obligationLoadPort;
  private final BonusCalculationAddRequestMapper addRequestMapper;
  private final BonusCalculationAddBusinessRuleValidator addBusinessRuleValidator;
  private final List<BonusStrategy> bonusStrategies;

  @Transactional
  @Override
  public void add(final BonusCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addBusinessRuleValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(addRequest.getQWeekId());
    final var qWeekId = qWeek.getId();
    final var bonusPrograms = bonusProgramLoadPort.loadAll();
    carLinkQuery.getActive().stream()
        .map(CarLinkResponse::getDriverId)
        .forEach(
            driverId -> {
              final var obligation =
                  obligationLoadPort.loadByDriverIdAndByQWeekId(driverId, qWeekId);
              if (obligation == null) {
         //       throw new RuntimeException(
         //           format(
         //               "Obligation was not calculated for %d - %d week, for driver.id = %d",
         //               qWeek.getYear(), qWeek.getNumber(), driverId));
         return;
              }

              final var weekPositiveAmount =
                  transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
                      .filter(transaction -> "P".equals(transaction.getKind()))
                      .map(TransactionResponse::getRealAmount)
                      .reduce(ZERO, BigDecimal::add);
              final var strategiesForCalculation = new ArrayList<BonusStrategy>();
              bonusPrograms.stream()
                  .map(
                      bonusProgram ->
                          bonusStrategies.stream()
                              .filter(bonusStrategy -> bonusStrategy.canApply(bonusProgram))
                              .findFirst())
                  .forEach(
                      bonusStrategyOpt ->
                          bonusStrategyOpt.ifPresent(strategiesForCalculation::add));

              for (final BonusStrategy strategy : strategiesForCalculation) {
                final var transactionAddRequests =
                    strategy.calculateBonus(obligation, weekPositiveAmount);

                for (final var transactionAddRequest : transactionAddRequests) {
                  final var bonusTransactionId = transactionAddUseCase.add(transactionAddRequest);
                  final var bonusCode = strategy.getBonusCode();
                  final var bonusProgramId = getBonusProgramIdByCode(bonusCode, bonusPrograms);
                  final var result =
                      BonusCalculationResult.builder()
                          .id(null)
                          .transactionId(bonusTransactionId)
                          .bonusProgramId(bonusProgramId)
                          .calculationId(null)
                          .build();
                  domain.getResults().add(result);
                }
              }
            });
    calculationAddPort.add(domain);

    sendEmails(domain.getResults(), qWeek.getNumber());
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Bonus Calculation took %d milli seconds \n", calculationDuration);
  }

  private Long getBonusProgramIdByCode(final String code, final List<BonusProgram> bonusPrograms) {
    return bonusPrograms.stream()
        .filter(bonusProgram -> code.equals(bonusProgram.getCode()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No Bonus Program with code: " + code))
        .getId();
  }

  private void sendEmails(final List<BonusCalculationResult> results, final Integer weekNumber) {
    final var operators = userAccountQuery.getAllOperators();
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).collect(toList());
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("calculationType", "Bonus");
    emailProperties.put("calculationDate", LocalDate.now());
    emailProperties.put("weekNumber", weekNumber);
    emailProperties.put(
        "transactions",
        transactionQuery.getAllByIds(
            results.stream().map(BonusCalculationResult::getTransactionId).collect(toList())));

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.BONUS_CALCULATION)
            .recipients(recipients)
            .properties(emailProperties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
