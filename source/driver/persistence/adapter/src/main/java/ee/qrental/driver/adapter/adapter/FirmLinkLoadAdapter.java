package ee.qrental.driver.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.adapter.mapper.FirmLinkAdapterMapper;
import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.api.out.FirmLinkLoadPort;
import ee.qrental.driver.domain.FirmLink;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkLoadAdapter implements FirmLinkLoadPort {

  private final FirmLinkRepository repository;
  private final FirmLinkAdapterMapper mapper;

  @Override
  public List<FirmLink> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public FirmLink loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }
  @Override
  public FirmLink loadOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDate) {
    return mapper.mapToDomain(repository.findOneByDriverIdAndRequiredDate(driverId, requiredDate));
  }
}
