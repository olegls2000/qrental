package ee.qrent.billing.deposit.persistence.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan("ee.qrent.billing.deposit.persistence.entity.jakarta")
public class DepositSpringDataConfig {
}
