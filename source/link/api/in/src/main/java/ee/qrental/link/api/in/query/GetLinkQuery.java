package ee.qrental.link.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.response.LinkResponse;

public interface GetLinkQuery extends BaseGetQuery<LinkUpdateRequest, LinkResponse> {}
