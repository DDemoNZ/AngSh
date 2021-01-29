package sh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.demo.models.Item;

import java.util.Optional;

@Repository
public interface ItemJpa extends JpaRepository<Item, Long> {

    Optional<Item> findByTitle(String title);

}
