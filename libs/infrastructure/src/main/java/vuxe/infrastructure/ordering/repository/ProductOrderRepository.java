package vuxe.infrastructure.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.common.entity.Product;

public interface ProductOrderRepository extends JpaRepository<Product, Long> {
}
