package ee.qrental.firm.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.firm.api.in.request.FirmAddRequest;
import ee.qrental.firm.domain.Firm;

public class FirmAddRequestMapper
        implements AddRequestMapper<FirmAddRequest, Firm> {

    @Override
    public Firm toDomain(FirmAddRequest request) {
        return Firm.builder()
                .id(null)
                .firmName(request.getFirmName())
                .iban(request.getIban())
                .regNumber(request.getRegNumber())
                .vatNumber(request.getVatNumber())
                .comment(request.getComment())
                .email(request.getEmail())
                .postAddress(request.getPostAddress())
                .phone(request.getPhone())
                .bank(request.getBank())
                .qGroup(request.getQGroup())
                .build();
    }
}
