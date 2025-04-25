package ee.qrent.billing.invoice.adapter.mapper;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.invoice.domain.Invoice;
import ee.qrent.billing.invoice.domain.InvoiceItem;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceItemJakartaEntity;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceJakartaEntity;

public class InvoiceAdapterMapper {

  public Invoice mapToDomain(final InvoiceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Invoice.builder()
        .id(entity.getId())
        .number(entity.getNumber())
        .driverId(entity.getDriverId())
        .qWeekId(entity.getQWeekId())
        .driverCompany(entity.getDriverCompany())
        .driverInfo(entity.getDriverInfo())
        .driverCompanyRegNumber(entity.getDriverCompanyRegNumber())
        .driverCompanyAddress(entity.getDriverCompanyAddress())
        .driverCompanyVat(entity.getDriverCompanyVatNumber())
        .qFirmId(entity.getQFirmId())
        .qFirmName(entity.getQFirmName())
        .qFirmRegNumber(entity.getQFirmRegNumber())
        .qFirmVatNumber(entity.getQFirmVatNumber())
        .qFirmIban(entity.getQFirmIban())
        .qFirmBank(entity.getQFirmBank())
        .qFirmEmail(entity.getQFirmEmail())
        .qFirmPostAddress(entity.getQFirmPostAddress())
        .qFirmPhone(entity.getQFirmPhone())
        .created(entity.getCreated())
        .balance(entity.getBalance())
        .currentWeekFee(entity.getCurrentWeekFee())
        .previousWeekBalanceFee(entity.getPreviousWeekBalanceFee())
        .previousWeekPositiveTxSum(entity.getPreviousWeekPositiveTxSum())
        .comment(entity.getComment())
        .items(entity.getItems().stream().map(this::mapToItemDomain).collect(toList()))
        .build();
  }

  public InvoiceItem mapToItemDomain(final InvoiceItemJakartaEntity entity) {
    return InvoiceItem.builder()
        .amount(entity.getAmount())
        .type(entity.getType())
        .description(entity.getDescription())
        .build();
  }

  public InvoiceJakartaEntity mapToEntity(final Invoice domain) {
    return InvoiceJakartaEntity.builder()
        .id(domain.getId())
        .id(domain.getId())
        .number(domain.getNumber())
        .driverId(domain.getDriverId())
        .qWeekId(domain.getQWeekId())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyVatNumber(domain.getDriverCompanyVat())
        .driverInfo(domain.getDriverInfo())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmId(domain.getQFirmId())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmIban(domain.getQFirmIban())
        .qFirmBank(domain.getQFirmBank())
        .qFirmEmail(domain.getQFirmEmail())
        .qFirmPostAddress(domain.getQFirmPostAddress())
        .qFirmPhone(domain.getQFirmPhone())
        .created(domain.getCreated())
        .balance(domain.getBalance())
        .currentWeekFee(domain.getCurrentWeekFee())
        .previousWeekBalanceFee(domain.getPreviousWeekBalanceFee())
        .previousWeekPositiveTxSum(domain.getPreviousWeekPositiveTxSum())
        .comment(domain.getComment())
        .build();
  }

  public InvoiceItemJakartaEntity mapToItemEntity(final InvoiceItem entity) {
    return InvoiceItemJakartaEntity.builder()
        .amount(entity.getAmount())
        .type(entity.getType())
        .description(entity.getDescription())
        .build();
  }
}
