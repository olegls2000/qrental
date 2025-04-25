package ee.qrent.billing.invoice.adapter.mapper;

import ee.qrent.billing.invoice.domain.InvoiceCalculation;
import ee.qrent.billing.invoice.domain.InvoiceCalculationResult;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceCalculationJakartaEntity;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceCalculationResultJakartaEntity;

public class InvoiceCalculationAdapterMapper {

  public InvoiceCalculation mapToDomain(final InvoiceCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return InvoiceCalculation.builder()
        .id(entity.getId())
        .startQWeekId(entity.getStartQWeekId())
        .endQWeekId(entity.getEndQWeekId())
        .actionDate(entity.getActionDate())
        .results(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private InvoiceCalculationResult mapToDomain(
      final InvoiceCalculationResultJakartaEntity resultEntity) {

    return InvoiceCalculationResult.builder().build();
  }
}
