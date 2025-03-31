package vuxe.infrastructure.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.common.entity.Product;

public interface ProductMgmtRepository extends JpaRepository<Product, Long> {
}
