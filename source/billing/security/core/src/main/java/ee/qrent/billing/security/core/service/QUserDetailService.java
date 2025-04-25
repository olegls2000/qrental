package ee.qrent.billing.security.core.service;

import ee.qrent.billing.security.core.QUserPrincipal;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class QUserDetailService implements UserDetailsService {

  private final GetUserAccountQuery userAccountQuery;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final var userAccount = userAccountQuery.getUserAccountByUsername(username);

    return new QUserPrincipal(userAccount);
  }
}
