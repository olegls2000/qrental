package ee.qrental.contract.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.invoice.api.in.request.ContractUpdateRequest;
import ee.qrental.invoice.api.out.ContractLoadPort;
import ee.qrental.invoice.domain.Contract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractUpdateRequestMapper
    implements UpdateRequestMapper<ContractUpdateRequest, Contract> {

  private final ContractLoadPort loadPort;

  @Override
  public Contract toDomain(final ContractUpdateRequest request) {
    final var invoiceFromDb = loadPort.loadById(request.getId());
    invoiceFromDb.setDriverCompany(request.getDriverCompany());
    invoiceFromDb.setDriverCompanyRegNumber(request.getDriverCompanyRegNumber());
    invoiceFromDb.setDriverCompanyAddress(request.getDriverCompanyAddress());
    invoiceFromDb.setQFirmName(request.getQFirmName());
    invoiceFromDb.setQFirmRegNumber(request.getQFirmRegNumber());
    invoiceFromDb.setQFirmVatNumber(request.getQFirmVatNumber());
    invoiceFromDb.setQFirmBank(request.getQFirmIban());
    invoiceFromDb.setComment(request.getComment());

    return invoiceFromDb;
  }

  @Override
  public ContractUpdateRequest toRequest(final Contract domain) {
    return ContractUpdateRequest.builder()
        .id(domain.getId())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmBank(domain.getQFirmBank())
        .qFirmIban(domain.getQFirmIban())
        .comment(domain.getComment())
        .build();
  }
}
