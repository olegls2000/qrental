package ee.qrent.billing.report.core.service;

import static ee.qrent.queue.api.in.EntryType.INVOICE_EMAIL;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportCalculationResult;
import ee.qrent.billing.report.domain.WeeklyReportDetail;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.billing.report.core.mapper.WeeklyReportAddRequestMapper;
import ee.qrent.common.out.port.AddPort;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseService implements WeeklyReportCalculationAddUseCase {

   private final AddRequestMapper<WeeklyReportCalculationAddRequest, WeeklyReportCalculation> addRequestMapper;
    private final AddPort<WeeklyReportCalculation> addPort;
   private final QDateTime qDateTime;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetCarLinkQuery carLink;
  private final GetBalanceQuery balanceQuery;
  private final WeeklyReportAddRequestMapper addRequestMapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;
  private final AddRequestValidator<WeeklyReportCalculationAddRequest> addRequestValidator;

  @Override
  public Long add(final WeeklyReportCalculationAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = request.getQWeekId();
    final var domain = addRequestMapper.toDomain(request);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);
    final var actionDate = qDateTime.getToday();
    domain.setActionDate(actionDate);

    driverQuery.getAll().stream()
        .forEach(
            driver -> {
              final var callSignLink =
                  callSignLinkQuery.getCallSignLinkByDriverIdAndDate(
                      driver.getId(), requestedQWeek.getEnd());
              final var callSign = callSignLink.getCallSign();
              final var activeCarLink =
                  carLink.getActiveByQWeekIdAndDriverId(request.getQWeekId(), driver.getId());
              final var carRegistrationNumber = activeCarLink.getRegistrationNumber();

              final var balance =
                  balanceQuery.getByDriverIdAndQWeekId(driver.getId(), request.getQWeekId());
              final var balanceAmount = balance.getAmount();

              final var weeklyReport =
                  WeeklyReport.builder()
                      .id(null)
                      .qWeekId(request.getQWeekId())
                      .driverId(driver.getId())
                      .driverName(format("%s %s", driver.getLastName(), driver.getFirstName()))
                      .driverTaxNumber(driver.getTaxNumber())
                      .callSign(callSign)
                      .carRegistrationNumber(carRegistrationNumber)
                      .weekYear(requestedQWeek.getYear())
                      .weekNumber(requestedQWeek.getNumber())
                      .startDate(requestedQWeek.getStart())
                      .endDate(requestedQWeek.getEnd())
                      .qFirmId(driver.getQFirmId())
                      .qFirmName(driver.getQFirmName())
                      .deposit(driver.getDeposit())
                      .paidDeposit(BigDecimal.valueOf(99999999))
                      // TODO ..
                      .status(null)
                      .balanceAmount(balanceAmount)
                      .build();

                final var result =
                        WeeklyReportCalculationResult.builder()
                                .weeklyReport(weeklyReport)
                                .transactionIds(Collections.emptySet())
                                .build();

                domain.getResults().add(result);
            });

     final var addedDomain = addPort.add(domain);
      sendEmails(domain);
      return addedDomain.getId();

  }

    private void sendEmails(InvoiceCalculation invoiceCalculation) {
        final var invoicesCount = invoiceCalculation.getResults().size();
        var handledInvoices = new AtomicInteger();
        invoiceCalculation.getResults().stream()
                .map(InvoiceCalculationResult::getInvoice)
                .sorted(comparing(Invoice::getNumber))
                .forEach(
                        invoice -> {
                            final var invoiceSum = invoice.getSum();
                            if (invoiceSum.compareTo(ZERO) == 0) {
                                System.out.println("Invoice Sum is 0 EUR, no need to send invoice to Driver");
                                progressTracking(handledInvoices, invoicesCount);

                                return;
                            }

                            final var driverId = invoice.getDriverId();
                            final var driver = driverQuery.getById(driverId);
                            if (!driver.getNeedInvoicesByEmail()) {
                                progressTracking(handledInvoices, invoicesCount);
                                System.out.println(
                                        "Sending Invoices by Email is not activated for Driver: "
                                                + driver.getFirstName()
                                                + ", "
                                                + driver.getLastName());
                                return;
                            }
                            final var recipient = driver.getEmail();
                            final var attachment = getAttachment(invoice);
                            final var properties = new HashMap<String, Object>();
                            properties.put("invoiceNumber", invoice.getNumber());
                            final var notificationQueuePushRequest =
                                    QueueEntryPushRequest.builder()
                                            .occurredAt(qDateTime.getNow())
                                            .type(INVOICE_EMAIL)
                                            .payloadRecipients(singletonList(recipient))
                                            .payloadAttachment(attachment)
                                            .payloadProperties(properties)
                                            .build();

                            notificationQueuePushUseCase.push(notificationQueuePushRequest);

                            progressTracking(handledInvoices, invoicesCount);
                            final var handledInvoicesInt = handledInvoices.getAndIncrement();
                            System.out.printf("Handled %d from %d invoices%n", handledInvoicesInt, invoicesCount);
                        });
    }


    private WeeklyReportDetail getWeeklyReportDetail(final Long qWeekId, final Long driverId) {
    final var obligation =  obligationQuery.getByQWeekIdAndDriverId(qWeekId, driverId);

    return WeeklyReportDetail.builder()
            .obligationTotal(obligation.getAmount())
            .obligationRent(?)


            .build();
  }
}
