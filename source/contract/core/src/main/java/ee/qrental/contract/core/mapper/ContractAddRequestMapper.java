package ee.qrental.contract.core.mapper;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.common.core.utils.QWeek;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.contract.api.in.request.ContractAddRequest;
import ee.qrental.contract.domain.Contract;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractAddRequestMapper implements AddRequestMapper<ContractAddRequest, Contract> {

  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetFirmQuery firmQuery;

  @Override
  public Contract toDomain(ContractAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var currentDate = LocalDate.now();
    final var contractNumber = getContractNumber(currentDate, driverId);
    final var renterName = getRenterName(driver);
    final var renterRegistrationNumber = getRenterName(driver);

    return Contract.builder()
        .id(null)
        .number(contractNumber)
        .renterName(renterName)
        .renterRegistrationNumber(driverId)
        .driverCompany(driver.getCompanyName())
        .driverCompanyAddress(driver.getCompanyAddress())
        .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
        .qFirmId(qFirmId)
        .qFirmName(qFirm.getFirmName())
        .qFirmRegNumber(qFirm.getRegNumber())
        .qFirmVatNumber(qFirm.getVatNumber())
        .qFirmBank(qFirm.getBank())
        .qFirmIban(qFirm.getIban())
        .items(getContractItems(filter))
        .created(LocalDate.now())
        .comment(request.getComment())
        .build();
  }

  private String getRenterName(final DriverResponse driver){
   final var driverCompanyName =  driver.getCompanyName();
   if(driverCompanyName == null || driverCompanyName.isEmpty()){
     final var driverFirstName = driver.getFirstName();
     final var driverLastName = driver.getLastName();
     return format("%s %s", driverFirstName, driverLastName);
   }
   return driverCompanyName;
  }

  private String getContractNumber(final LocalDate currentDate, final Long driverId){
    final var dateString = currentDate.format(ofPattern("yyyyMMdd"));
    return format("%s-%d", dateString,driverId);
  }



  private String getContractNumber(final Integer year, final QWeek week, final Long driverId) {
    final var weekNumber = week.getNumber();
    return String.format("%d%d%d", year, weekNumber, driverId);
  }

  private List<ContractItem> getContractItems(final YearAndWeekAndDriverAndFeeFilter filter) {
    final Map<String, List<TransactionResponse>> transactionsGroupedByType =
        getTransactionsGroupedByType(filter);

    return transactionsGroupedByType.entrySet().stream()
        .map(entry -> getContractItem(entry.getKey(), entry.getValue()))
        .collect(toList());
  }

  private Map<String, List<TransactionResponse>> getTransactionsGroupedByType(
      final YearAndWeekAndDriverAndFeeFilter filter) {
    return transactionQuery.getAllByFilter(filter).stream()
        .collect(groupingBy(TransactionResponse::getType));
  }

  private ContractItem getContractItem(
      final String transactionType, final List<TransactionResponse> transactions) {
    return ContractItem.builder()
        .type(transactionType)
        .amount(
            transactions.stream()
                .map(TransactionResponse::getRealAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add))
        .build();
  }
}
