package org.order.repository;

import org.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
