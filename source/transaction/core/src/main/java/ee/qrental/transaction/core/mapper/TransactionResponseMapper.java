package ee.qrental.transaction.core.mapper;

import static java.lang.String.format;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionResponseMapper implements ResponseMapper<TransactionResponse, Transaction> {

  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetDriverQuery driverQuery;

  @Override
  public TransactionResponse toResponse(final Transaction domain) {
    final var driverId = domain.getDriverId();
    final var callSign = callSignLinkQuery.getCallSignLinkByDriverId(driverId).getCallSign();
    final var driverInfo = driverQuery.getObjectInfo(driverId);

    return TransactionResponse.builder()
        .id(domain.getId())
        .realAmount(domain.getRealAmount())
        .type(domain.getType().getName())
        .driverInfo(driverInfo)
        .callSign(callSign)
        .date(domain.getDate())
        .weekNumber(domain.getWeekNumber())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final Transaction domain) {
    final var type = domain.getType().getName();
    final var realAmount = domain.getRealAmount();
    final var date = domain.getDate().toString();
    final var weekNumber = domain.getWeekNumber();
    final var callSignLinkResponse =
        callSignLinkQuery.getCallSignLinkByDriverId(domain.getDriverId());

    return format(
        "Transaction: %s %s EURO, "
            + "week number: %d (%s), "
            + "for driver: %s "
            + "with call sign: %d",
        type,
        realAmount,
        weekNumber,
        date,
        callSignLinkResponse.getDriverInfo(),
        callSignLinkResponse.getCallSign());
  }
}
