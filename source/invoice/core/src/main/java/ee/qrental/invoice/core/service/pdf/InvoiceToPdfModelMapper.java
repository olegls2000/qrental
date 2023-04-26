package ee.qrental.invoice.core.service.pdf;

import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.SHORT;
import static java.util.stream.Collectors.toMap;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import java.math.BigDecimal;

public class InvoiceToPdfModelMapper {

  public InvoicePdfModel getPdfModel(final Invoice invoice) {
    final var number = invoice.getNumber();
    final var creationDate = invoice.getCreated().format(ofLocalizedDate(SHORT));
    final var weekNumber = invoice.getWeekNumber();
    final var year = invoice.getCreated().getYear();
    final var startDate =
        QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
    final var endDate =
        QTimeUtils.getLastDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
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
            .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
            .reduce(BigDecimal::add)
            .orElseThrow(
                () -> new RuntimeException("No Negative Transactions during Invoice period."));

    final var vatPercentage = invoice.withVat() ? BigDecimal.valueOf(20) : BigDecimal.ZERO;
    final var vatAmount = sum.multiply(vatPercentage.movePointLeft(2));

    final var driverCompanyVat = invoice.getDriverCompanyVat();
    final var sumWithVat = sum.add(vatAmount);
    final var items =
        invoice.getItems().stream()
            .collect(toMap(InvoiceItem::getDescription, InvoiceItem::getAmount));

    final var balanceAmount = invoice.getBalance();
    final var debt = getDebt(balanceAmount);
    final var advancePayment = getAdvancePayment(balanceAmount);
    final var total = sumWithVat.add(debt).subtract(advancePayment);
    final var fee = BigDecimal.ZERO;
    final var totalWithFee = total.add(fee);

    return InvoicePdfModel.builder()
        .number(number)
        .creationDate(creationDate)
        .weekNumber(weekNumber)
        .year(year)
        .startDate(startDate)
        .endDate(endDate)
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
        .fee(fee)
        .totalWithFee(totalWithFee)
        .build();
  }

  private BigDecimal getDebt(final BigDecimal balanceAmount) {
    if (balanceAmount.signum() < 0) {
      return balanceAmount.negate();
    }
    return BigDecimal.ZERO;
  }

  private BigDecimal getAdvancePayment(final BigDecimal balanceAmount) {
    if (balanceAmount.signum() > 0) {
      return balanceAmount;
    }
    return BigDecimal.ZERO;
  }
}
