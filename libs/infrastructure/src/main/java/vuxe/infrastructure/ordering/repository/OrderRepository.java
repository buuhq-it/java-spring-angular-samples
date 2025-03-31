package vuxe.infrastructure.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.ordering.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
