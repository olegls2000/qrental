package ee.qrent.billing.constant.persistence.mapper;

import ee.qrent.billing.constant.domain.Constant;
import ee.qrent.billing.constant.persistence.entity.jakarta.ConstantJakartaEntity;

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
