package ee.qrental.invoice.adapter.adapter;

import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.*;
import ee.qrental.invoice.api.out.*;
import ee.qrental.invoice.domain.InvoiceCalculation;
import ee.qrental.invoice.domain.InvoiceCalculationResult;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationResultJakartaEntity;
import ee.qrental.invoice.entity.jakarta.InvoiceTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationPersistenceAdapter
    implements InvoiceCalculationAddPort, InvoiceCalculationDeletePort {

  private final InvoiceCalculationRepository invoiceCalculationRepository;
  private final InvoiceCalculationResultRepository invoiceCalculationResultRepository;
  private final InvoiceRepository invoiceRepository;
  private final InvoiceItemRepository invoiceItemRepository;
  private final InvoiceTransactionRepository invoiceTransactionRepository;

  private final InvoiceAdapterMapper invoiceMapper;

  @Override
  public InvoiceCalculation add(final InvoiceCalculation domain) {
    final InvoiceCalculationJakartaEntity invoiceCalculationEntity =
        InvoiceCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .startQWeekId(domain.getStartQWeekId())
            .endQWeekId(domain.getEndQWeekId())
            .comment(domain.getComment())
            .build();

    final var invoiceCalculationEntitySaved =
        invoiceCalculationRepository.save(invoiceCalculationEntity);

    final var invoiceCalculationResults = domain.getResults();
    for (InvoiceCalculationResult result : invoiceCalculationResults) {
      final var invoice = result.getInvoice();
      final var invoiceEntity = invoiceMapper.mapToEntity(invoice);
      final var invoiceEntitySaved = invoiceRepository.save(invoiceEntity);
      final var invoiceItems = invoice.getItems();
      invoiceItems.stream()
          .map(invoiceMapper::mapToItemEntity)
          .forEach(
              invoiceItemEntity -> {
                invoiceItemEntity.setInvoice(invoiceEntitySaved);
                invoiceItemRepository.save(invoiceItemEntity);
              });
      final var invoiceCalculationResultEntity =
          InvoiceCalculationResultJakartaEntity.builder()
              .id(null)
              .calculation(invoiceCalculationEntitySaved)
              .invoice(invoiceEntitySaved)
              .build();
      invoiceCalculationResultRepository.save(invoiceCalculationResultEntity);

      final var transactionIds = result.getTransactionIds();
      for (Long transactionId : transactionIds) {
        final var invoiceTransactionEntity =
            InvoiceTransactionJakartaEntity.builder()
                .transactionId(transactionId)
                .invoice(invoiceEntitySaved)
                .build();
        invoiceTransactionRepository.save(invoiceTransactionEntity);
      }
    }
    return null;
  }

  @Override
  public void delete(final Long id) {}
}
