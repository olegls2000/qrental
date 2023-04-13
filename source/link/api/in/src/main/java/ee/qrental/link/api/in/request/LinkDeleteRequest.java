package ee.qrental.link.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class LinkDeleteRequest extends AbstractDeleteRequest {
  public LinkDeleteRequest(final Long id) {
    super(id);
  }
}
