package demo.data.resiliency.account.balance.repository;

import demo.data.resiliency.account.balance.domain.Balance;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * Create Read Update and Delete operations for account balance
 * @author Gregory Green
 */
@Repository
public interface BalanceRepository extends KeyValueRepository<Balance,String> {
}
