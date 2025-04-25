package ee.qrent.billing.user.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.request.UserAccountUpdateRequest;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import ee.qrent.billing.user.core.mapper.UserAccountResponseMapper;
import ee.qrent.billing.user.core.mapper.UserAccountUpdateRequestMapper;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountQueryService implements GetUserAccountQuery {

  private final UserAccountLoadPort loadPort;
  private final UserAccountResponseMapper mapper;
  private final UserAccountUpdateRequestMapper updateRequestMapper;

  @Override
  public List<UserAccountResponse> getAll() {

    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(getLastNameComparator())
        .collect(toList());
  }

  @Override
  public UserAccountResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public UserAccountUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  private Comparator<UserAccountResponse> getLastNameComparator() {
    return (user1, user2) -> {
      final var lastName1 = user1.getLastName();
      final var lastName2 = user2.getLastName();
      return lastName1.compareTo(lastName2);
    };
  }

  @Override
  public List<UserAccountResponse> getAllOperators() {
    return loadPort.loadByRoleName("Operation").stream()
        .map(mapper::toResponse)
        .sorted(getLastNameComparator())
        .collect(toList());
  }

  @Override
  public UserAccountResponse getUserAccountByUsername(final String username) {
    return mapper.toResponse(loadPort.loadByUsername(username));
  }
}
