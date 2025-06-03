package ee.qrent.billing.bolt.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsAddRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsDeleteRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;

public class BoltStatisticsRequestValidator
    implements AddRequestValidator<BoltStatisticsAddRequest>,
        UpdateRequestValidator<BoltStatisticsUpdateRequest>,
        DeleteRequestValidator<BoltStatisticsDeleteRequest> {

  @Override
  public ViolationsCollector validate(final BoltStatisticsAddRequest request) {

    return null;
  }

  @Override
  public ViolationsCollector validate(final BoltStatisticsDeleteRequest request) {

    return null;
  }

  @Override
  public ViolationsCollector validate(final BoltStatisticsUpdateRequest request) {

    return null;
  }
}
