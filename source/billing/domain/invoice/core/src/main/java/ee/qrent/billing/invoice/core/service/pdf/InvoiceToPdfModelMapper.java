package ee.qrent.billing.invoice.core.service.pdf;
import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.math.BigDecimal.ZERO;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.SHORT;
import static java.util.stream.Collectors.toMap;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.invoice.domain.Invoice;
import ee.qrent.billing.invoice.domain.InvoiceItem;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceToPdfModelMapper {

  private static final BigDecimal VAT_RATE_2023 = BigDecimal.valueOf(20);
  private static final BigDecimal VAT_RATE_2024 = BigDecimal.valueOf(22);

  private final InvoiceLoadPort invoiceLoadPort;
  private final GetQWeekQuery qWeekQuery;

  public InvoicePdfModel getPdfModel(final Invoice invoice) {
    final var number = invoice.getNumber();
    final var invoiceWeek = qWeekQuery.getById(invoice.getQWeekId());
    final var weekNumber = invoiceWeek.getNumber();
    final var year = invoiceWeek.getYear();
    final var previousWeek = qWeekQuery.getOneBeforeById(invoice.getQWeekId());
    final var startDate = invoiceWeek.getStart().format(ofLocalizedDate(SHORT));

    final var endDate = invoiceWeek.getEnd().format(ofLocalizedDate(SHORT));
    final var feeStartDate = previousWeek.getStart().format(ofLocalizedDate(SHORT));
    final var feeEndDate = previousWeek.getEnd().format(ofLocalizedDate(SHORT));

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
    final var sum = qRound(invoice.getSum());

    final var vatPercentage = getVatRate(invoice, invoiceWeek);
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
    final var currentWeekFee = invoice.getCurrentWeekFee();
    final var previousWeekBalanceFee = invoice.getPreviousWeekBalanceFee().negate();
    final var totalWithFee = total.add(currentWeekFee).add(previousWeekBalanceFee);
    final var block2AValue =
        getTotalAmountWithFeeFromPreviousInvoice(invoice.getDriverId(), invoice.getQWeekId());

    return InvoicePdfModel.builder()
        .number(number)
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
        .sum(qRound(sum))
        .vatPercentage(qRound(vatPercentage))
        .vatAmount(qRound(vatAmount))
        .sumWithVat(qRound(sumWithVat))
        .items(items)
        .debt(qRound(debt))
        .advancePayment(qRound(advancePayment))
        .total(qRound(total))
        .previousWeekBalanceFee(qRound(previousWeekBalanceFee))
        .currentWeekFee(qRound(currentWeekFee))
        .totalWithFee(qRound(totalWithFee))
        .block2A(qRound(block2AValue))
        .block2B(qRound(invoice.getPreviousWeekPositiveTxSum()))
        .build();
  }

  private BigDecimal getVatRate(final Invoice invoice, final QWeekResponse invoiceWeek) {
    if (invoice.withVat()) {
      return invoiceWeek.getYear() == 2023 ? VAT_RATE_2023 : VAT_RATE_2024;
    }

    return ZERO;
  }

  private BigDecimal getTotalAmountWithFeeFromPreviousInvoice(
      final Long driverId, final Long currentQWeekId) {
    final var previousQWeek = qWeekQuery.getOneBeforeById(currentQWeekId);

    final var previousInvoice =
        invoiceLoadPort.loadByQWeekIdAndDriverId(previousQWeek.getId(), driverId);

    if (previousInvoice == null) {
      return ZERO;
    }
    final var sum = previousInvoice.getSum();
    final var withVat = previousInvoice.withVat();
    final var vatPercentage = withVat ? VAT_RATE_2023 : ZERO;
    final var vatAmount = sum.multiply(vatPercentage.movePointLeft(2));
    final var sumWithVat = sum.add(vatAmount);
    final var balanceAmount = previousInvoice.getBalance();
    final var debt = getDebt(balanceAmount);
    final var advancePayment = getAdvancePayment(balanceAmount);
    final var total = sumWithVat.add(debt).subtract(advancePayment);
    final var currentWeekFee = previousInvoice.getCurrentWeekFee();
    final var previousWeekBalanceFee = previousInvoice.getPreviousWeekBalanceFee().negate();
    final var totalWithFee = total.add(currentWeekFee).add(previousWeekBalanceFee);

    return totalWithFee;
  }

  private BigDecimal getDebt(final BigDecimal balanceAmount) {
    if (balanceAmount.signum() < 0) {
      final var debt = balanceAmount.negate();
      return debt;
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
