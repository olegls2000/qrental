package ee.qrental.link.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.link.adapter.mapper.LinkAdapterMapper;
import ee.qrental.link.adapter.repository.LinkRepository;
import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.domain.Link;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkLoadAdapter implements LinkLoadPort {

  private final LinkRepository repository;
  private final LinkAdapterMapper mapper;

  @Override
  public List<Link> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Link loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Link loadActiveByDriverId(final Long driverId) {
    final var nowDate = LocalDate.now();

    return mapper.mapToDomain(repository.findActiveByDriverIdAndNowDate(driverId, nowDate));
  }

  @Override
  public List<Link> loadActiveByCarId(final Long carId) {
    final var nowDate = LocalDate.now();

    return repository.findActiveByCarIdAndNowDate(carId, nowDate).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
