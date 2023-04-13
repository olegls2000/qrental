package ee.qrental.invoice.core.service;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;

import ee.qrental.invoice.api.in.request.InvoiceCalculationRequest;
import ee.qrental.invoice.api.in.request.InvoiceDeleteRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationUseCase;
import ee.qrental.invoice.api.out.InvoiceAddPort;
import ee.qrental.invoice.api.out.InvoiceDeletePort;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.api.out.InvoiceUpdatePort;
import ee.qrental.invoice.core.mapper.InvoiceCalculationRequestMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService implements InvoiceCalculationUseCase {

  private final InvoiceAddPort addPort;
  private final InvoiceUpdatePort updatePort;
  private final InvoiceDeletePort deletePort;
  private final InvoiceLoadPort loadPort;
  private final InvoiceCalculationRequestMapper calculationRequestMapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;
  private final InvoiceBusinessRuleValidator businessRuleValidator;

  @Override
  public void calculate(InvoiceCalculationRequest request) {
    final var domain = calculationRequestMapper.toDomain(request);
    final var actionDateFormal = getLastSundayFromDate(domain.getActionDate());
  }

  public void delete(final InvoiceDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Invoice failed. No Record with id = " + id);
    }
  }
}
