package ee.qrental.bonus.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.adapter.mapper.WeekObligationAdapterMapper;
import ee.qrental.bonus.adapter.repository.WeekObligationRepository;
import java.util.List;

import ee.qrental.bonus.api.out.WeekObligationLoadPort;
import ee.qrental.bonus.domain.WeekObligation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeekObligationLoadAdapter implements WeekObligationLoadPort {

  private final WeekObligationRepository repository;
  private final WeekObligationAdapterMapper mapper;

  @Override
  public List<WeekObligation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public WeekObligation loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }
}
