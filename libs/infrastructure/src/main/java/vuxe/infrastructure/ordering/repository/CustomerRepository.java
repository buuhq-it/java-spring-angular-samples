package vuxe.infrastructure.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.ordering.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
