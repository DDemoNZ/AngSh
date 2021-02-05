package sh.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sh.demo.models.Item;

import java.util.List;

public interface ItemService {

    Item saveItem(Item item);

    Page<Item> getAllItems(Pageable pageable);

    List<Item> getAllItems();

}
