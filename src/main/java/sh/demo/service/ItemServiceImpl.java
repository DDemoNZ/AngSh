package sh.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sh.demo.models.dto.Item;
import sh.demo.models.dto.ItemResponseDto;
import sh.demo.repository.ItemJpa;

import org.springframework.data.domain.Pageable;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemJpa itemJpa;

    public ItemServiceImpl(ItemJpa itemJpa) {
        this.itemJpa = itemJpa;
    }

    @Override
    public Object saveItem(Item item) {
        if (itemJpa.findByTitle(item.getTitle()).isEmpty()) {
            return new ItemResponseDto(itemJpa.save(item), null);
        } else {
            return new ItemResponseDto(null, "Item already exist");
        }
    }

    @Override
    public Page<Item> getAllItems(Pageable pageable) {
        Page<Item> content = itemJpa.findAll(pageable);
        return content;
    }
}
