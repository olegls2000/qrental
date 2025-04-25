package ee.qrent.billing.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.billing.contract.api.in.request.AbsenceAddRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceDeleteRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.AbsenceAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.AbsenceDeleteUseCase;
import ee.qrent.billing.contract.api.in.usecase.AbsenceUpdateUseCase;
import ee.qrent.billing.contract.api.out.AbsenceAddPort;
import ee.qrent.billing.contract.api.out.AbsenceDeletePort;
import ee.qrent.billing.contract.api.out.AbsenceUpdatePort;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.contract.core.mapper.AbsenceAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceUpdateRequestMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class AbsenceUseCaseService
    implements AbsenceAddUseCase, AbsenceUpdateUseCase, AbsenceDeleteUseCase {
  private final AbsenceAddPort addPort;
  private final AbsenceUpdatePort updatePort;
  private final AbsenceDeletePort deletePort;
  private final AbsenceAddRequestMapper addRequestMapper;
  private final AbsenceUpdateRequestMapper updateRequestMapper;
  private final AddRequestValidator<AbsenceAddRequest> addRequestValidator;
  private final UpdateRequestValidator<AbsenceUpdateRequest> updateRequestValidator;
  private final DeleteRequestValidator<AbsenceDeleteRequest> deleteRequestValidator;

  @Override
  public Long add(final AbsenceAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var absence = addRequestMapper.toDomain(request);
    final var savedAbsence = addPort.add(absence);

    return savedAbsence.getId();
  }

  @Override
  public void update(final AbsenceUpdateRequest request) {
    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var absence = updateRequestMapper.toDomain(request);
    updatePort.update(absence);
  }

  @Override
  public void delete(final AbsenceDeleteRequest request) {
    final var violationsCollector = deleteRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    deletePort.delete(request.getId());
  }
}
