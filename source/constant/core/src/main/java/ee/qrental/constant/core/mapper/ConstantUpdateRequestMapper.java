package ee.qrental.constant.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.domain.Constant;

public class ConstantUpdateRequestMapper implements UpdateRequestMapper<ConstantUpdateRequest, Constant> {

    @Override
    public Constant toDomain(final ConstantUpdateRequest request) {
        return Constant.builder()
                .id(request.getId())
                .constant(request.getConstant())
                .value(request.getValue())
                .description(request.getDescription())
                .negative(request.getNegative())
                .build();
    }

    @Override
    public ConstantUpdateRequest toRequest(final Constant domain) {
        return ConstantUpdateRequest.builder()
                .id(domain.getId())
                .constant(domain.getConstant())
                .value(domain.getValue())
                .description(domain.getDescription())
                .negative(domain.getNegative())
                .build();
    }
}
