package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Balance;
import demo.data.resiliency.account.balance.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Service to read balance information
 * @author Gregory Green
 */
@Component
@RequiredArgsConstructor
public class ReadBalanceFunction implements Function<String, Balance> {

    private final BalanceRepository repository;

    /**
     *
     * @param id the account balance identifier
     * @return the balance of the account
     */
    @Override
    public Balance apply(String id) {
        return repository.findById(id).orElse(new Balance(id, BigDecimal.ZERO,null));
    }
}
