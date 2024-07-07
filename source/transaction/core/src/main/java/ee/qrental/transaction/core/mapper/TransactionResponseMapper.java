package ee.qrental.transaction.core.mapper;

import static ee.qrental.common.utils.QStringUtils.contract;
import static java.lang.String.format;


import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionResponseMapper implements ResponseMapper<TransactionResponse, Transaction> {
  private static final int COMMENT_MAX_SIZE = 26;

  private final GetDriverQuery driverQuery;

  @Override
  public TransactionResponse toResponse(final Transaction domain) {
    final var driverId = domain.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var callSign = driver.getCallSign();
    final var driverInfo = format("%s %s ", driver.getFirstName(), driver.getLastName());

    final var kind = domain.getType().getKind();
    final var transactionKindCode = kind == null ? null : kind.getCode();

    return TransactionResponse.builder()
        .id(domain.getId())
        .realAmount(domain.getRealAmount())
        .type(domain.getType().getName())
        .typeDescription(domain.getType().getDescription())
        .kind(transactionKindCode)
        .driverId(domain.getDriverId())
        .driverInfo(driverInfo)
        .callSign(callSign)
        .date(domain.getDate())
        .weekNumber(domain.getWeekNumber())
        .raw(domain.isRaw())
        .comment(domain.getComment())
        .invoiceIncluded(domain.getType().getInvoiceIncluded())
        .commentShorten(contract(domain.getComment(), COMMENT_MAX_SIZE))
        .build();
  }

  @Override
  public String toObjectInfo(final Transaction domain) {
    final var driverId = domain.getDriverId();
    final var type = domain.getType().getName();
    final var realAmount = domain.getRealAmount();
    final var date = domain.getDate().toString();
    final var weekNumber = domain.getWeekNumber();
    final var driver = driverQuery.getById(driverId);
    final var callSign = driver.getCallSign();
    final var driverInfo = format("%s %s ", driver.getFirstName(), driver.getLastName());

    return format(
        "Transaction: %s %s EURO, "
            + "week number: %d (%s), "
            + "for driver: %s "
            + "with call sign: %d",
        type, realAmount, weekNumber, date, driverInfo, callSign);
  }
}
