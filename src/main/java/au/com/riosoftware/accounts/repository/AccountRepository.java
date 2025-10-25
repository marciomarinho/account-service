package au.com.riosoftware.accounts.repository;

import au.com.riosoftware.accounts.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
    Flux<Account> findByUserId(String userId);
}