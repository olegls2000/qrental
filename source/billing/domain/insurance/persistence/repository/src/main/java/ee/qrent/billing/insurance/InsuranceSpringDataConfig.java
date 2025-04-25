package ee.qrent.billing.insurance;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan("ee.qrent.billing.insurance.persistence.entity.jakarta")
public class InsuranceSpringDataConfig {}
