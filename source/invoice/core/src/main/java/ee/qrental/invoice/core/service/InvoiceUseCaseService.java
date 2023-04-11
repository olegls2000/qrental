package ee.qrental.invoice.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrental.invoice.api.in.request.InvoiceAddRequest;
import ee.qrental.invoice.api.in.request.InvoiceDeleteRequest;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceAddUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceDeleteUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceUpdateUseCase;
import ee.qrental.invoice.api.out.InvoiceAddPort;
import ee.qrental.invoice.api.out.InvoiceDeletePort;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.api.out.InvoiceUpdatePort;
import ee.qrental.invoice.core.mapper.InvoiceAddRequestMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class InvoiceUseCaseService
    implements InvoiceAddUseCase, InvoiceUpdateUseCase, InvoiceDeleteUseCase {

  private final InvoiceAddPort addPort;
  private final InvoiceUpdatePort updatePort;
  private final InvoiceDeletePort deletePort;
  private final InvoiceLoadPort loadPort;
  private final InvoiceAddRequestMapper addRequestMapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;
  private final InvoiceBusinessRuleValidator businessRuleValidator;

  @Override
  public Long add(final InvoiceAddRequest request) {
    final var invoice = addRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateAdd(invoice);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }
    final var savedInvoice = addPort.add(invoice);

    return savedInvoice.getId();
  }

  @Override
  public void update(final InvoiceUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final InvoiceDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Invoice failed. No Record with id = " + id);
    }
  }
}
