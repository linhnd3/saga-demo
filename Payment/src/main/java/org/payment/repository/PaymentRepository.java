package org.payment.repository;

import org.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
