package ee.qrent.billing.bolt.core.service;

import ee.qrent.billing.bolt.api.in.query.GetBoltOrdersCountQuery;
import ee.qrent.billing.bolt.api.in.response.BoltOrdersCountResponse;
import ee.qrent.billing.bolt.api.out.BoltOrdersCountLoadPort;
import ee.qrent.billing.bolt.core.mapper.BoltOrdersCountResponseMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@AllArgsConstructor
public class BoltOrdersCountQueryService implements GetBoltOrdersCountQuery {

  private final BoltOrdersCountLoadPort loadPort;
  private final BoltOrdersCountResponseMapper mapper;

  @Override
  public List<BoltOrdersCountResponse> getAllByYearAndMonth(
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
