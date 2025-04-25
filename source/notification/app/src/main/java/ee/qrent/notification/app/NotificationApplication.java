package ee.qrent.notification.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ee.qrent.notification", "ee.qrent.common", "ee.qrent.queue"})
public class NotificationApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }
}
