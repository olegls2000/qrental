package ee.qrent.billing.report.core.service.pdf;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_BONUS_FRIEND_CODE;

@AllArgsConstructor
public class WeeklyReportToPdfModelMapper {

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignQuery callSignQuery;
  private final GetCarQuery carQuery;

  public WeeklyReportPdfModel getPdfModel(final WeeklyReport report) {
    final var driver = driverQuery.getById(report.getDriverId());
    final var callSign = callSignQuery.getById(report.getCallSignId());
    final var currentWeekId = report.getQWeekId();
    final var currentWeek = qWeekQuery.getById(currentWeekId);
    final var previousWeek = qWeekQuery.getOneBeforeById(currentWeekId);
    final var nextWeek = qWeekQuery.getOneAfterById(currentWeekId);
    final var carReg =
        report.getCarId() != null ? carQuery.getById(report.getCarId()).getRegNumber() : null;
    final var totalRentAmountRaw = getTotalRentAmount(report.getTransactionTypesVsAmount());
    final var totalRentAmount =
        totalRentAmountRaw.compareTo(BigDecimal.ZERO) > 0 ? BigDecimal.ZERO : totalRentAmountRaw;
    final var totalExternalSystemsIncomeAmount =
        getTotalExternalSystemsIncomeAmount(report.getTransactionTypesVsAmount());
    final var boltExternalSystemsIncomeAmount =
        report
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_BOLT_PLUS_CODE, BigDecimal.ZERO);
    final var otherPaymentAmount =
        totalExternalSystemsIncomeAmount.subtract(boltExternalSystemsIncomeAmount);

    final var balanceOnSunday = report.getBalanceAmountSunday();
    final var distributedObligationAmount = getDistributedObligationAmount(report);
    final var totalOtherPaymentAmount =
        getTotalOtherPaymentAmount(
            report.getTransactionTypesVsAmount(), distributedObligationAmount);

    final var totalPaymentAmountRaw =
        totalRentAmount
            .add(totalExternalSystemsIncomeAmount)
            .add(totalOtherPaymentAmount)
            .add(getDamagePaymentAmount(report));

    final var totalPaymentAmount =
        totalPaymentAmountRaw.compareTo(BigDecimal.ZERO) > 0
            ? BigDecimal.ZERO
            : totalPaymentAmountRaw;

    return WeeklyReportPdfModel.builder()
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .language(driver.getCommunicationLanguage())
        .idNumber(driver.getTaxNumber())
        .callSign(callSign.getCallSign())
        .reportedWeekNumber(previousWeek.getNumber())
        .previousWeekStart(previousWeek.getStart())
        .previousWeekEnd(previousWeek.getEnd())
        .currentWeekStart(currentWeek.getStart())
        .currentWeekEnd(currentWeek.getEnd())
        .nextWeekStart(nextWeek.getStart())
        .nextWeekEnd(nextWeek.getEnd())
        .feeAmountSunday(report.getFeeAmountSunday())
        .feeAmountAtCalculationMoment(report.getFeeAmountAtCalculationMoment())
        .carRegistrationNumber(carReg)
        .depositObligation(report.getDepositObligation())
        .depositPaid(report.getDepositPaid())
        .balanceAmountSunday(report.getBalanceAmountSunday())
        .debtAmountSunday(
            balanceOnSunday.compareTo(BigDecimal.ZERO) < 0 ? balanceOnSunday : BigDecimal.ZERO)
        .balanceAmountAtCalculationMoment(report.getBalanceAmountAtCalculationMoment())
        .weeksCountTillEnd(report.getWeeksCountTillEnd())
        .obligationStatus(
            report.getObligationStatus() != null ? report.getObligationStatus().name() : null)
        .currentObligationAmount(report.getCurrentObligationAmount())
        .netAmountOnThursday(report.getNetAmountOnThursday())
        .totalRentAmount(totalRentAmount)
        .incomeTotal(totalExternalSystemsIncomeAmount)
        .incomeBolt(boltExternalSystemsIncomeAmount)
        .incomeOthers(totalOtherPaymentAmount)
        .totalOtherPaymentAmount(otherPaymentAmount)
        .transactionTypesVsAmount(report.getTransactionTypesVsAmount())
        .insuranceCases(getInsuranceCases(report))
        .comment(report.getComment())
        .amount(
            report.getBalanceAmountAtCalculationMoment() != null
                ? report.getBalanceAmountAtCalculationMoment()
                : report.getBalanceAmountSunday())
        .distributedObligationAmount(distributedObligationAmount)
        .totalPaymentAmount(totalPaymentAmount)
        .build();
  }

  private BigDecimal getDistributedObligationAmount(final WeeklyReport report) {
    final var totalRentAmount = getTotalRentAmount(report.getTransactionTypesVsAmount());
    final var balanceOnSunday = report.getBalanceAmountSunday();
    final var nominalDistributedObligationAmount =
        totalRentAmount
            .add(getInsuranceAmount(report.getTransactionTypesVsAmount()))
            .multiply(new BigDecimal(0.25));
    if (balanceOnSunday.compareTo(BigDecimal.ZERO)
        >= 0) { // balanceOnSunday = (5..0], nominalDistributedObligationAmount = -4
      return BigDecimal.ZERO;
    }
    if (balanceOnSunday.compareTo(nominalDistributedObligationAmount)
        < 0) { // balanceOnSunday = -5, nominalDistributedObligationAmount = -2
      return nominalDistributedObligationAmount;
    }
    return balanceOnSunday; // balanceOnSunday = -1, nominalDistributedObligationAmount = -2
  }

  private List<WeeklyReportPdfInsuranceCase> getInsuranceCases(final WeeklyReport report) {
    return report.getInsuranceCases().stream()
        .map(
            icDomain ->
                WeeklyReportPdfInsuranceCase.builder()
                    .damageRemaining(icDomain.getDamageRemaining())
                    .occurrenceDate(icDomain.getOccurrenceDate())
                    .carRegNumber(icDomain.getCarRegNumber())
                    .build())
        .collect(Collectors.toList());
  }

  private BigDecimal getTotalRentAmount(final Map<String, BigDecimal> transactionTypesVsAmount) {
    final var rentAmount =
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE, BigDecimal.ZERO);
    final var bonusReliablePartnerAmount =
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_BONUS_RELIABLE_PARTNER_CODE, BigDecimal.ZERO);
    final var bonusBoltAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_BONUS_BOLT_CODE, BigDecimal.ZERO);
    final var bonusPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_BONUS_PLUS_CODE, BigDecimal.ZERO);
    final var bonusFriendAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_BONUS_FRIEND_CODE, BigDecimal.ZERO);
    final var bonusNewDriverAmount =
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_BONUS_NEW_DRIVER_CODE, BigDecimal.ZERO);

    return rentAmount
        .add(bonusReliablePartnerAmount)
        .add(bonusBoltAmount)
        .add(bonusFriendAmount)
        .add(bonusPlusAmount)
        .add(bonusNewDriverAmount);
  }

  private BigDecimal getTotalExternalSystemsIncomeAmount(
      final Map<String, BigDecimal> transactionTypesVsAmount) {
    final var boltPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_BOLT_PLUS_CODE, BigDecimal.ZERO);
    final var bankPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_BANK_PLUS_CODE, BigDecimal.ZERO);
    final var cashPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_CASH_PLUS_CODE, BigDecimal.ZERO);
    final var otherPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_OTHER_PLUS_CODE, BigDecimal.ZERO);
    final var paycheckPlusAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_PAYCHECK_PLUS_CODE, BigDecimal.ZERO);

    return boltPlusAmount
        .add(bankPlusAmount)
        .add(cashPlusAmount)
        .add(otherPlusAmount)
        .add(paycheckPlusAmount);
  }

  private BigDecimal getTotalOtherPaymentAmount(
      final Map<String, BigDecimal> transactionTypesVsAmount,
      final BigDecimal distributedObligationAmount) {
    final var depositAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_DEPOSIT_CODE, BigDecimal.ZERO);
    var innerInsuranceAmount =
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE, BigDecimal.ZERO);
    transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_DEPOSIT_CODE, BigDecimal.ZERO);
    final var nonLabelFineAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_NO_LABEL_FINE_CODE, BigDecimal.ZERO);
    final var feeAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_FEE_DEBT_CODE, BigDecimal.ZERO);
    final var parkingFineAmount =
        transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_PARKING_FINE_CODE, BigDecimal.ZERO);

    return depositAmount
        .add(getInsuranceAmount(transactionTypesVsAmount))
        .add(nonLabelFineAmount)
        .add(feeAmount)
        .add(parkingFineAmount)
        .add(distributedObligationAmount);
  }

  private BigDecimal getDamagePaymentAmount(final WeeklyReport report) {

    return report
        .getTransactionTypesVsAmount()
        .getOrDefault(TRANSACTION_TYPE_DAMAGE_PAYMENT_CODE, BigDecimal.ZERO);
  }

  private BigDecimal getInsuranceAmount(final Map<String, BigDecimal> transactionTypesVsAmount) {
    var innerInsuranceAmount =
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE, BigDecimal.ZERO);
    transactionTypesVsAmount.getOrDefault(TRANSACTION_TYPE_DEPOSIT_CODE, BigDecimal.ZERO);
    return innerInsuranceAmount.add(
        transactionTypesVsAmount.getOrDefault(
            TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_MANUAL_CODE, BigDecimal.ZERO));
  }
}
