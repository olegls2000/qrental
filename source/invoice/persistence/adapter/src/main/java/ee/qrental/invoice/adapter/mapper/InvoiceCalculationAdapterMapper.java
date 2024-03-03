package ee.qrental.invoice.adapter.mapper;

import ee.qrental.invoice.domain.InvoiceCalculation;
import ee.qrental.invoice.domain.InvoiceCalculationResult;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationResultJakartaEntity;

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
