package org.payment.repository;

import jakarta.transaction.Transactional;
import org.payment.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
public interface BalanceRepository extends JpaRepository<Balance, String> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Balance b WHERE b.balance >= :amount AND b.username = :username")
    boolean existsByBalanceAndUsername(@Param("amount") Long amount, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Balance b SET b.balance = b.balance - :amount WHERE b.username = :username")
    void updateBalanceSubtractAmount(@Param("amount") Long amount, @Param("username") String username);
}
