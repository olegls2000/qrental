package ee.qrent.billing.firm.persistence.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan("ee.qrent.billing.firm.persistence.entity.jakarta")
public class FirmSpringDataConfig {
}
