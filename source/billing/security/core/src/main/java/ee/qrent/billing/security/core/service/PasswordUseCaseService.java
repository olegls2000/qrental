package ee.qrent.billing.security.core.service;

import ee.qrent.billing.security.api.in.usecase.PasswordUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class PasswordUseCaseService implements PasswordUseCase {

    private PasswordEncoder passwordEncoder;

    @Override
    public String encode(final String pwd) {
      return   passwordEncoder.encode(pwd);
    }
}
