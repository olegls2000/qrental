package ee.qrent.billing.invoice.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.invoice.api.in.request.InvoiceAddRequest;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;

import ee.qrent.billing.invoice.domain.Invoice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceAddRequestValidator implements AddRequestValidator<InvoiceAddRequest> {

  private final InvoiceLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final InvoiceAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(request, violationsCollector);

    return violationsCollector;
  }

  private void checkUniqueness(
      final InvoiceAddRequest request, final ViolationsCollector violationCollector) {

    // TODO switch to qWeekId

    final Invoice invoiceFromDb = null;
    /* loadPort.loadByWeekAndDriverAndFirm(
    request.getQWeekId(), request.getDriverId(), request.getQFirmId());*/
    if (invoiceFromDb == null) {
      return;
    }
    violationCollector.collect("Invoice for used Driver, Week, and Q-firm, already exist");
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of Invoice failed. No Record with id = " + id);
    }
  }
}
