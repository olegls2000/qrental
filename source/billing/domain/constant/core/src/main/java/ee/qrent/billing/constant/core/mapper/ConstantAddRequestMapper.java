package ee.qrent.billing.constant.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.constant.api.in.request.ConstantAddRequest;
import ee.qrent.billing.constant.domain.Constant;

public class ConstantAddRequestMapper
        implements AddRequestMapper<ConstantAddRequest, Constant> {

    @Override
    public Constant toDomain(ConstantAddRequest request) {
        return Constant.builder()
                .id(null)
                .constant(request.getConstant())
                .value(request.getValue())
                .description(request.getDescription())
                .negative(request.getNegative())
                .build();
    }
}
