package sh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.demo.models.Orders;
import sh.demo.models.User;

import java.util.Collection;

@Repository
public interface OrderJpa extends JpaRepository<Orders, Long> {

    Collection<Orders> findAllByUser(User user);

}
