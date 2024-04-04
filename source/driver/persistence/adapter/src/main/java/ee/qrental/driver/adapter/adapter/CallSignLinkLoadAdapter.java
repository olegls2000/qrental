package ee.qrental.driver.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.adapter.mapper.CallSignLinkAdapterMapper;
import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.domain.CallSignLink;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkLoadAdapter implements CallSignLinkLoadPort {

  private final CallSignLinkRepository repository;
  private final CallSignLinkAdapterMapper mapper;

  @Override
  public List<CallSignLink> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public CallSignLink loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<CallSignLink> loadByCallSignId(final Long callSignId) {
    return repository.findAllByCallSignId(callSignId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public CallSignLink loadActiveByCallSignId(final Long callSignId) {
    return mapper.mapToDomain(repository.findOneByDateEndIsNullAndCallSignId(callSignId));
  }

  @Override
  public CallSignLink loadActiveByDriverId(final Long driverId) {
    return mapper.mapToDomain(repository.findOneByDateEndIsNullAndDriverId(driverId));
  }

  @Override
  public List<CallSignLink> loadByDriverId(final Long driverId) {
    return repository.findAllByDriverId(driverId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public CallSignLink loadByDriverIdAndDate(final Long driverId, final LocalDate date) {
    return mapper.mapToDomain(repository.findActiveByDriverIdAndNowDate(driverId, date));
  }

  @Override
  public List<CallSignLink> loadActiveByDate(final LocalDate date) {
    return repository.findActiveByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<CallSignLink> loadClosedByDate(final LocalDate date) {
    return repository.findClosedByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Long loadCountActiveByDate(final LocalDate date) {
    return repository.findCountActiveByDate(date);
  }

  @Override
  public Long loadCountClosedByDate(final LocalDate date) {
    return repository.findCountClosedByDate(date);
  }
}
