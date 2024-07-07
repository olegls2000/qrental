package ee.qrental.driver.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.FirmLinkResponse;
import ee.qrental.driver.api.in.request.FirmLinkUpdateRequest;

import java.time.LocalDate;

public interface GetFirmLinkQuery
    extends BaseGetQuery<FirmLinkUpdateRequest, FirmLinkResponse> {

    FirmLinkResponse getOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDate);
}