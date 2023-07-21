package ee.qrental.link.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.response.LinkResponse;

import java.util.Optional;

public interface GetLinkQuery extends BaseGetQuery<LinkUpdateRequest, LinkResponse> {
   LinkResponse getActiveLinkByDriverId(final Long driverId);
}
