package ee.qrent.billing.task.core.service;

import ee.qrent.billing.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;
import ee.qrent.billing.task.api.out.TaskRunResultLoadPort;
import ee.qrent.billing.task.core.mapper.TaskRunResultResponseMapper;
import ee.qrent.billing.task.api.in.query.filter.ResultFilter;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class TaskRunQueryService implements GetTaskRunResultQuery {

  private final TaskRunResultLoadPort loadPort;
  private final TaskRunResultResponseMapper mapper;

  @Override
  public List<TaskRunResultResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public TaskRunResultResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public TaskRunResultResponse getUpdateRequestById(Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public List<TaskRunResultResponse> getAllByName(final String taskName) {

    return loadPort.loadAllByTaskName(taskName).stream().map(mapper::toResponse).toList();
  }

  @Override
  public List<TaskRunResultResponse> getAllByFilter(final ResultFilter filterRequest) {

    switch (filterRequest.getState()) {
      case Q_WEEK_CREATION_TASK -> {
        return getAllByName("QWEEK-CREATION-TASK");
      }
      case INSURANCE_CALCULATION_TASK -> {
        return getAllByName("INSURANCE-CALCULATION-TASK");
      }
      case RENT_CALCULATION_TASK -> {
        return getAllByName("RENT-CALCULATION-TASK");
      }
      case OBLIGATION_CALCULATION_TASK -> {
        return getAllByName("OBLIGATION-CALCULATION-TASK");
      }
      case WEEKLY_MONDAY_REPORT_TASK -> {
        return getAllByName("MONDAY-BILLING_WEEKLY-REPORT-TASK");
      }
    }

    return getAll();
  }
}
