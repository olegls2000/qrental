package ee.qrental.driver.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.CallSignLinkResponse;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.request.FirmLinkResponse;
import ee.qrental.driver.api.in.request.FirmLinkUpdateRequest;

import java.time.LocalDate;

public interface GetFirmLinkQuery
    extends BaseGetQuery<FirmLinkUpdateRequest, FirmLinkResponse> {

    FirmLinkResponse getOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDate);
}