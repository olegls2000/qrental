package ee.qrental.bonus.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.adapter.mapper.BonusCalculationAdapterMapper;
import ee.qrental.bonus.adapter.repository.BonusCalculationRepository;
import ee.qrental.bonus.api.out.BonusCalculationLoadPort;
import ee.qrental.bonus.domain.BonusCalculation;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationLoadAdapter implements BonusCalculationLoadPort {

  private final BonusCalculationRepository repository;
  private final BonusCalculationAdapterMapper mapper;

  @Override
  public Long loadLastCalculatedQWeekId() {
    return repository.getLastCalculationQWeekId();
  }

  @Override
  public List<BonusCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public BonusCalculation loadById(Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
