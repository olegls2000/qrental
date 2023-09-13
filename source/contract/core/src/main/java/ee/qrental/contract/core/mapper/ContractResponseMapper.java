package ee.qrental.contract.core.mapper;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.contract.api.in.response.ContractResponse;
import ee.qrental.contract.domain.Contract;
import java.util.HashMap;

public class ContractResponseMapper implements ResponseMapper<ContractResponse, Contract> {
  @Override
  public ContractResponse toResponse(final Contract domain) {
    return ContractResponse.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .build();
  }

  @Override
  public String toObjectInfo(final Contract domain) {
    return format(
        "Receiver: %s, Sender: %s, Week: %d",
        domain.getDriverCompany(), domain.getQFirmName(), domain.getWeekNumber());
  }

}
