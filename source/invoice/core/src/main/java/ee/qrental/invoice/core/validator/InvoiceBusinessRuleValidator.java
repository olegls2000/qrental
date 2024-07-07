package ee.qrental.invoice.core.validator;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.domain.Invoice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceBusinessRuleValidator implements QValidator<Invoice> {

  private final InvoiceLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(Invoice domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(Invoice domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    return null;
  }

  private void checkUniqueness(final Invoice domain, final ViolationsCollector violationCollector) {
    final var invoiceFromDb =
        loadPort.loadByWeekAndDriverAndFirm(
            domain.getQWeekId(), domain.getDriverId(), domain.getQFirmId());
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
