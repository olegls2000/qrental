package ee.qrent.ui.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class QUserDetailServiceTest {

  //@Test
  void getUserDetails() {
    final var decoder = new BCryptPasswordEncoder();
    final var password = "1234A";
    final var passwordHash = decoder.encode(password);

    assertEquals("??", passwordHash);
  }
}
