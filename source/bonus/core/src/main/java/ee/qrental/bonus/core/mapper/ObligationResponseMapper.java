package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.api.in.response.ObligationResponse;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.common.core.in.mapper.ResponseMapper;

public class ObligationResponseMapper implements ResponseMapper<ObligationResponse, Obligation> {
    @Override
    public ObligationResponse toResponse(Obligation domain) {
        return null;
    }

    @Override
    public String toObjectInfo(Obligation domain) {
        return null;
    }
}
