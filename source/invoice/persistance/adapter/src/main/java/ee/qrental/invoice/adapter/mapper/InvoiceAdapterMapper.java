package ee.qrental.invoice.adapter.mapper;

import static java.util.stream.Collectors.toList;

import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import ee.qrental.invoice.entity.jakarta.InvoiceItemJakartaEntity;
import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;

public class InvoiceAdapterMapper {

  public Invoice mapToDomain(final InvoiceJakartaEntity entity) {
    return Invoice.builder()
        .id(entity.getId())
        .number(entity.getNumber())
        .driverId(entity.getDriverId())
        .driverCallSign(entity.getDriverCallSign())
        .driverCompany(entity.getDriverCompany())
        .driverCompanyRegNumber(entity.getDriverCompanyRegNumber())
        .driverCompanyAddress(entity.getDriverCompanyAddress())
        .qFirmId(entity.getQFirmId())
        .qFirmName(entity.getQFirmName())
        .qFirmRegNumber(entity.getQFirmRegNumber())
        .qFirmVatNumber(entity.getQFirmVatNumber())
        .qFirmIban(entity.getQFirmIban())
        .qFirmBank(entity.getQFirmBank())
        .created(entity.getCreated())
        .comment(entity.getComment())
        .items(entity.getItems().stream().map(this::mapToItemDomain).collect(toList()))
        .build();
  }

  private InvoiceItem mapToItemDomain(final InvoiceItemJakartaEntity entity) {
    return InvoiceItem.builder().amount(entity.getAmount()).type(entity.getType()).build();
  }

  public InvoiceJakartaEntity mapToEntity(final Invoice domain) {
    return InvoiceJakartaEntity.builder()
        .id(domain.getId())
        .id(domain.getId())
        .number(domain.getNumber())
        .driverId(domain.getDriverId())
        .driverCallSign(domain.getDriverCallSign())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmId(domain.getQFirmId())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmIban(domain.getQFirmIban())
        .qFirmBank(domain.getQFirmBank())
        .created(domain.getCreated())
        .comment(domain.getComment())
        .items(domain.getItems().stream().map(this::mapToItemEntity).collect(toList()))
        .build();
  }

  private InvoiceItemJakartaEntity mapToItemEntity(final InvoiceItem entity) {
    return InvoiceItemJakartaEntity.builder()
        .amount(entity.getAmount())
        .type(entity.getType())
        .build();
  }
}
