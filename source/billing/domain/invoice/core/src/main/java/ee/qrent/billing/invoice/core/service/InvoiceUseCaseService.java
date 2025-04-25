package ee.qrent.billing.invoice.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.invoice.api.in.request.InvoiceAddRequest;
import ee.qrent.billing.invoice.api.in.request.InvoiceDeleteRequest;
import ee.qrent.billing.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceAddUseCase;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceDeleteUseCase;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceUpdateUseCase;
import ee.qrent.billing.invoice.api.out.InvoiceAddPort;
import ee.qrent.billing.invoice.api.out.InvoiceDeletePort;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.invoice.api.out.InvoiceUpdatePort;
import ee.qrent.billing.invoice.core.mapper.InvoiceAddRequestMapper;
import ee.qrent.billing.invoice.core.mapper.InvoiceUpdateRequestMapper;
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
  private final AddRequestValidator<InvoiceAddRequest> addRequestValidator;

  @Override
  public Long add(final InvoiceAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }
    final var domain = addRequestMapper.toDomain(request);
    final var savedInvoice = addPort.add(domain);

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
