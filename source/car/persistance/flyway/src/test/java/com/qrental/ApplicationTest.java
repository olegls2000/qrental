package com.qrental;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@Slf4j
@ExtendWith(OutputCaptureExtension.class)
class ApplicationTest {

  @Test
  void runApplication(final CapturedOutput output) {
    assertDoesNotThrow(() -> com.qrental.Application.main(new String[] {}));

    final var out = output.toString();
    //assertFalse(out.contains("WARN"));
    assertFalse(out.contains("ERROR"));
    assertFalse(out.contains("not eligible for getting processed by all BeanPostProcessors"));
    assertFalse(out.contains("SLF4J: Class path contains multiple SLF4J bindings."));
    assertFalse(out.contains("Unable to proxy interface-implementing method"));
  }
}
