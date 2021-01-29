package sh.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import sh.demo.models.Item;
import sh.demo.service.ItemService;

import javax.annotation.PostConstruct;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostConstruct
    public void saveInit() {
        for (int i = 0; i < 30; i++) {
            Item item = new Item();
            item.setTitle("Item" + i);
            item.setPrice((long) (Math.random() * 1000));
            item.setImageUrl("item4");
            saveItem(item);
        }
    }

    @PostMapping
    public Object saveItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @GetMapping(params = {"page", "size"})
    public Page<Item> getAllItems(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Item> itemPage = itemService.getAllItems(PageRequest.of(page, size));
        if (page > itemPage.getTotalPages()) {
            throw new NoSuchElementException("Last page");
        }
        return itemPage;
    }
}
