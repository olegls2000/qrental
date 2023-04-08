package ee.qrental.firm.core.mapper;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.firm.api.in.response.FirmResponse;
import ee.qrental.firm.domain.Firm;

import static java.lang.String.format;

public class FirmResponseMapper
        implements ResponseMapper<FirmResponse, Firm> {
    @Override
    public FirmResponse toResponse(final Firm domain) {
    return FirmResponse.builder()
        .id(domain.getId())
        .firmName(domain.getFirmName())
        .iban(domain.getIban())
        .regNumber(domain.getRegNumber())
        .vatNumber(domain.getVatNumber())
        .comment(domain.getComment())
        .email(domain.getEmail())
        .postAddress(domain.getPostAddress())
        .phone(domain.getPhone())
        .bank(domain.getBank())
        .qGroup(domain.getQGroup())
        .build();
    }

    @Override
    public String toObjectInfo(Firm domain) {
        return format("%s %s",
                domain.getFirmName(),
                domain.getRegNumber());
    }
}
