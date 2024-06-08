package demo.data.resiliency.account.balance;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@Configuration
@EnableMapRepositories
@Profile("in-memory")
public class InMemoryRepositoryConfig {
}
