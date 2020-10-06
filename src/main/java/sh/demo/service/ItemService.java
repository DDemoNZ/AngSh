package sh.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sh.demo.models.dto.Item;

import java.util.Collection;

public interface ItemService {
    Object saveItem(Item item);

    Collection<Item> getAllItems(Pageable pageable);
}
