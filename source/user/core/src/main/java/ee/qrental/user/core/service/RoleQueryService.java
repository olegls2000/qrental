package ee.qrental.user.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.user.api.in.query.GetRoleQuery;
import ee.qrental.user.api.in.request.RoleUpdateRequest;
import ee.qrental.user.api.in.response.RoleResponse;
import ee.qrental.user.api.out.RoleLoadPort;
import ee.qrental.user.core.mapper.RoleResponseMapper;
import ee.qrental.user.core.mapper.RoleUpdateRequestMapper;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleQueryService implements GetRoleQuery {

  private final RoleLoadPort loadPort;
  private final RoleResponseMapper mapper;
  private final RoleUpdateRequestMapper updateRequestMapper;

  @Override
  public List<RoleResponse> getAll() {

    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(getNameComparator())
        .collect(toList());
  }

  @Override
  public RoleResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public RoleUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  private Comparator<RoleResponse> getNameComparator() {
    return (role1, role2) -> {
      final var name1 = role1.getName();
      final var name2 = role2.getName();
      return name1.compareTo(name2);
    };
  }
}
