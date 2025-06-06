package ee.qrent.billing.bolt.core.service;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.bolt.api.in.response.BoltRidesCountResponse;
import ee.qrent.billing.bolt.api.out.BoltRidesCountLoadPort;
import ee.qrent.billing.bolt.core.mapper.BoltRidesCountResponseMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@AllArgsConstructor
public class BoltOrdersCountQueryService implements GetBoltRidesCountQuery {

  private final BoltRidesCountLoadPort loadPort;
  private final BoltRidesCountResponseMapper mapper;

  @Override
  public List<BoltRidesCountResponse> getAllByYearAndMonth(
      final Integer year, final Integer month) {
    return loadPort.loadAllByYearAndMonth(year, month).stream()
        .filter(
            distinctByKey(counter -> counter.getYear().toString() + counter.getMonth().toString()))
        .map(mapper::toResponse)
        .toList();
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }
}
