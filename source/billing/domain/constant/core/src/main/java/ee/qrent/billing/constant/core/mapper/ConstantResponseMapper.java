package ee.qrent.billing.constant.core.mapper;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.response.constant.ConstantResponse;
import ee.qrent.billing.constant.domain.Constant;

import static java.lang.String.format;

public class ConstantResponseMapper
        implements ResponseMapper<ConstantResponse, Constant> {
    @Override
    public ConstantResponse toResponse(final Constant domain) {
    return ConstantResponse.builder()
        .id(domain.getId())
            .constant(domain.getConstant())
            .value(domain.getValue())
            .description(domain.getDescription())
            .negative(domain.getNegative())
            .build();
    }

    @Override
    public String toObjectInfo(Constant domain) {
        return format("%s %s",
                domain.getDescription(),
                domain.getValue());
    }
}
