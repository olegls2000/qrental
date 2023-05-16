package ee.qrental.invoice.core.service.pdf;

import static java.math.BigDecimal.ZERO;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.SHORT;
import static java.util.stream.Collectors.toMap;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import java.math.BigDecimal;

public class InvoiceToPdfModelMapper {

  private static final BigDecimal VAT_RATE = BigDecimal.valueOf(20);

  public InvoicePdfModel getPdfModel(final Invoice invoice) {
    final var number = invoice.getNumber();
    final var creationDate = invoice.getCreated().format(ofLocalizedDate(SHORT));
    final var weekNumber = invoice.getWeekNumber();
    final var previousWeekNumber = weekNumber - 1;
    final var year = invoice.getCreated().getYear();
    final var startDate =
        QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
    final var endDate =
        QTimeUtils.getLastDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
    final var feeStartDate =
        QTimeUtils.getFirstDayOfWeekInYear(year, previousWeekNumber).format(ofLocalizedDate(SHORT));
    final var feeEndDate =
        QTimeUtils.getLastDayOfWeekInYear(year, previousWeekNumber).format(ofLocalizedDate(SHORT));

    final var driverInfo = invoice.getDriverInfo();
    final var driverCompany = invoice.getDriverCompany();
    final var driverCompanyRegNumber = invoice.getDriverCompanyRegNumber();
    final var driverCompanyAddress = invoice.getDriverCompanyAddress();
    final var qFirmName = invoice.getQFirmName();
    final var qFirmEmail = invoice.getQFirmEmail();
    final var qFirmPostAddress = invoice.getQFirmPostAddress();
    final var qFirmPhone = invoice.getQFirmPhone();
    final var qFirmRegNumber = invoice.getQFirmRegNumber();
    final var qFirmVatNumber = invoice.getQFirmVatNumber();
    final var qFirmBank = invoice.getQFirmBank();
    final var qFirmIban = invoice.getQFirmIban();
    final var sum =
        invoice.getItems().stream()
            .map(InvoiceItem::getAmount)
            .reduce(BigDecimal::add)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Invoice has no Items. Please check invoice creation logic."))
            .negate();

    final var withVat = invoice.withVat();
    final var vatPercentage = withVat ? VAT_RATE : ZERO;
    final var vatAmount = sum.multiply(vatPercentage.movePointLeft(2));

    final var driverCompanyVat = invoice.getDriverCompanyVat();
    final var sumWithVat = sum.add(vatAmount);
    final var items =
        invoice.getItems().stream()
            .collect(toMap(InvoiceItem::getDescription, InvoiceItem::getAmount));

    final var balanceAmount = invoice.getBalance();
    final var debt = getDebt(balanceAmount, vatPercentage);
    final var advancePayment = getAdvancePayment(balanceAmount);
    final var total = sumWithVat.add(debt).subtract(advancePayment);
    final var currentWeekFee = invoice.getCurrentWeekFee();
    final var previousWeekBalanceFee = invoice.getPreviousWeekBalanceFee();
    final var totalWithFee = total.add(currentWeekFee).add(previousWeekBalanceFee);

    return InvoicePdfModel.builder()
        .number(number)
        .creationDate(creationDate)
        .weekNumber(weekNumber)
        .year(year)
        .startDate(startDate)
        .endDate(endDate)
        .feeStartDate(feeStartDate)
        .feeEndDate(feeEndDate)
        .driverInfo(driverInfo)
        .driverCompany(driverCompany)
        .driverCompanyRegNumber(driverCompanyRegNumber)
        .driverCompanyAddress(driverCompanyAddress)
        .driverCompanyVat(driverCompanyVat)
        .qFirmName(qFirmName)
        .qFirmEmail(qFirmEmail)
        .qFirmPostAddress(qFirmPostAddress)
        .qFirmPhone(qFirmPhone)
        .qFirmRegNumber(qFirmRegNumber)
        .qFirmVatNumber(qFirmVatNumber)
        .qFirmBank(qFirmBank)
        .qFirmIban(qFirmIban)
        .sum(sum)
        .vatPercentage(vatPercentage)
        .vatAmount(vatAmount)
        .sumWithVat(sumWithVat)
        .items(items)
        .debt(debt)
        .advancePayment(advancePayment)
        .total(total)
        .previousWeekBalanceFee(previousWeekBalanceFee)
        .currentWeekFee(currentWeekFee)
        .totalWithFee(totalWithFee)
        .build();
  }

  private BigDecimal getDebt(final BigDecimal balanceAmount, final BigDecimal vatPercentage) {
    if (balanceAmount.signum() < 0) {
      final var debt = balanceAmount.negate();
      final var debtVat = debt.multiply(vatPercentage.movePointLeft(2));

      return debt.add(debtVat);
    }

    return ZERO;
  }

  private BigDecimal getAdvancePayment(final BigDecimal balanceAmount) {
    if (balanceAmount.signum() > 0) {
      return balanceAmount;
    }
    return ZERO;
  }
}
