package ee.qrent.billing.user.core.service;

import ee.qrent.billing.user.api.in.request.RoleAddRequest;
import ee.qrent.billing.user.api.in.request.RoleDeleteRequest;
import ee.qrent.billing.user.api.in.request.RoleUpdateRequest;
import ee.qrent.billing.user.api.in.usecase.RoleAddUseCase;
import ee.qrent.billing.user.api.in.usecase.RoleDeleteUseCase;
import ee.qrent.billing.user.api.in.usecase.RoleUpdateUseCase;
import ee.qrent.billing.user.api.out.RoleAddPort;
import ee.qrent.billing.user.api.out.RoleDeletePort;
import ee.qrent.billing.user.api.out.RoleLoadPort;
import ee.qrent.billing.user.api.out.RoleUpdatePort;
import ee.qrent.billing.user.core.mapper.RoleAddRequestMapper;
import ee.qrent.billing.user.core.mapper.RoleUpdateRequestMapper;
import ee.qrent.billing.user.core.validator.RoleRequestValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleUseCaseService implements RoleAddUseCase, RoleUpdateUseCase, RoleDeleteUseCase {

  private final RoleAddPort addPort;
  private final RoleUpdatePort updatePort;
  private final RoleDeletePort deletePort;
  private final RoleLoadPort loadPort;
  private final RoleAddRequestMapper addRequestMapper;
  private final RoleUpdateRequestMapper updateRequestMapper;
  private final RoleRequestValidator requestValidator;

  @Transactional
  @Override
  public Long add(final RoleAddRequest request) {
    final var violationsCollector = requestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }
    final var domain = addRequestMapper.toDomain(request);
    final var savedDomain = addPort.add(domain);

    return savedDomain.getId();
  }

  @Override
  public void update(final RoleUpdateRequest request) {
    final var violationsCollector = requestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final RoleDeleteRequest request) {
    final var violationsCollector = requestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return;
    }
    deletePort.delete(request.getId());
  }
}
