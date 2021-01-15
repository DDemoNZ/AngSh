package sh.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sh.demo.models.dto.Item;

public interface ItemService {
    Object saveItem(Item item);

    Page<Item> getAllItems(Pageable pageable);


}
