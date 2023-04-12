package ee.qrental.constant.adapter.mapper;

import ee.qrental.constant.domain.Constant;
import ee.qrental.constant.entity.jakarta.ConstantJakartaEntity;

public class ConstantAdapterMapper {

    public Constant mapToDomain(final ConstantJakartaEntity entity) {
        return Constant.builder()
                .id(entity.getId())
                .constant(entity.getConstant())
                .value(entity.getValue())
                .description(entity.getDescription())
                .negative(entity.getNegative())
                .build();
    }

    public ConstantJakartaEntity mapToEntity(final Constant domain) {
        return ConstantJakartaEntity.builder()
                .id(domain.getId())
                .constant(domain.getConstant())
                .value(domain.getValue())
                .description(domain.getDescription())
                .negative(domain.getNegative())
                .build();
    }
}
