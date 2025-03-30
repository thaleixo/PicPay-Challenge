package com.picpay.banking.interview.domain.repository;
import com.picpay.banking.interview.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findBySymbol(String symbol);
}