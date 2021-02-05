package sh.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sh.demo.models.Item;
import sh.demo.models.dto.ItemResponseDto;
import sh.demo.repository.ItemJpa;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemJpa itemJpa;

    public ItemServiceImpl(ItemJpa itemJpa) {
        this.itemJpa = itemJpa;
    }

    @Override
    public Item saveItem(Item item) {
        return itemJpa.save(item);
    }

    @Override
    public Page<Item> getAllItems(Pageable pageable) {

        Page<Item> content = itemJpa.findAll(pageable);
        return content;
    }

    @Override
    public List<Item> getAllItems() {
        return itemJpa.findAll();
    }
}
