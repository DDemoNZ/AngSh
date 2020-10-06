package sh.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import sh.demo.models.dto.Item;
import sh.demo.service.ItemService;

import javax.annotation.PostConstruct;
import java.util.Collection;

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
        Item item1 = new Item();
        item1.setTitle("Item 1");
        item1.setPrice(100L);

        Item item2 = new Item();
        item2.setTitle("Item 2");
        item2.setPrice(120L);

        Item item3 = new Item();
        item3.setTitle("Item 3");
        item3.setPrice(300L);

        Item item4 = new Item();
        item4.setTitle("Item 4");
        item4.setPrice(150L);

        Item item5= new Item();
        item5.setTitle("Item 5");
        item5.setPrice(250L);

        Item item6 = new Item();
        item6.setTitle("Item 6");
        item6.setPrice(200L);

        Item item7 = new Item();
        item7.setTitle("Item 7");
        item7.setPrice(270L);

        Item item8 = new Item();
        item8.setTitle("Item 8");
        item8.setPrice(1200L);

        Item item9 = new Item();
        item9.setTitle("Item 9");
        item9.setPrice(999L);

        Item item10 = new Item();
        item10.setTitle("Item 10");
        item10.setPrice(100L);

        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);
        itemService.saveItem(item4);
        itemService.saveItem(item5);
        itemService.saveItem(item6);
        itemService.saveItem(item7);
        itemService.saveItem(item8);
        itemService.saveItem(item9);
        itemService.saveItem(item10);
    }

    @PostMapping
    public Object saveItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @GetMapping(params = {"page", "size"})
    public Collection<Item> getAllItems(@RequestParam("page") int page, @RequestParam("size") int size) {
//        ItemPage itemPage = new ItemPage(itemService.getAllItems(PageRequest.of(page, size)), null);
        Collection<Item> items = itemService.getAllItems(PageRequest.of(page, size));
        return items;
    }

}
