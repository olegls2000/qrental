package ee.qrent.billing.bolt.core.service;

import com.opencsv.bean.CsvToBeanBuilder;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsAddRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsDeleteRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsAddUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsDeleteUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsUpdateUseCase;
import ee.qrent.billing.bolt.api.out.*;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

@AllArgsConstructor
public class BoltStatisticsUseCaseService
    implements BoltStatisticsAddUseCase, BoltStatisticsUpdateUseCase, BoltStatisticsDeleteUseCase {

  private final BoltStatisticsAddPort addPort;
  private final BoltStatisticsUpdatePort updatePort;
  private final BoltStatisticsDeletePort deletePort;
  private final BoltStatisticsAddRequestMapper addRequestMapper;
  private final BoltStatisticsUpdateRequestMapper updateRequestMapper;
  private final BoltOrdersCountAddPort boltOrdersCountAddPort;
  private final GetDriverQuery driverQuery;
  private final GetQWeekQuery qWeekQuery;

  @Transactional(REQUIRES_NEW)
  @Override
  public Long add(final BoltStatisticsAddRequest request) {
    final var individualDriversVsOrders =
        new CsvToBeanBuilder<BoltStatisticsCsvRecord>(
                new InputStreamReader(new ByteArrayInputStream(request.getData())))
                .withType(BoltStatisticsCsvRecord.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .stream()
                .collect(
                    groupingBy(
                        BoltStatisticsCsvRecord::getIndividualId,
                        summingInt(BoltStatisticsCsvRecord::getFinishedOrders)));
    final var year = request.getYear();
    final var month = request.getMonth();
    final var qWeeks = qWeekQuery.getAllByYearAndMonth(year, month);

    individualDriversVsOrders.entrySet().stream()
        .forEach(
            entry -> {
              final var boltId = entry.getKey();
              final var driver = driverQuery.getDriverByBoltId(boltId);
              if (driver == null) {
                // final var driverName = individualDriversVsDrivers.get(boltId);
                throw new RuntimeException(
                    format("Driver %s must have Bolt Id: %s", "driverName", boltId));
              }

              final var driverId = driver.getId();
              final var ordersCounter = entry.getValue();
              qWeeks.forEach(
                  qweek ->
                      boltOrdersCountAddPort.add(
                          BoltOrdersCount.builder()
                              .driverId(driverId)
                              .boltId(boltId)
                              .qWeekId(qweek.getId())
                              .month(month)
                              .year(year)
                              .monthOrdersCount(ordersCounter)
                              .build()));
            });

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final BoltStatisticsUpdateRequest request) {
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final BoltStatisticsDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
