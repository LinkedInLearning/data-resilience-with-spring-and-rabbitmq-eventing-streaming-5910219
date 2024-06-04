package demo.data.resiliency.account.balance.repository;

import demo.data.resiliency.account.balance.domain.Balance;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends KeyValueRepository<Balance,String> {
}
