package sh.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import sh.demo.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class ItemServiceImplTest {

    public static final int expectedTotalItems = 36;
    public static final int expectedTotalPages = 12;
    @Autowired
    ItemService itemService;

    List<Item> expectedStoredItems = new ArrayList<>();
    private final Item storedItem = new Item("titleQ", 200L, "itemUrl");

    @Test
    void saveItem() {
        List<Item> allItems = itemService.getAllItems();
        Long lastId = allItems.get(allItems.size() - 1).getId();

        Item item = itemService.saveItem(storedItem);
        Item item1 = itemService.saveItem(new Item("titleW", 200L, "itemUrl"));
        Item item2 = itemService.saveItem(new Item("titleE", 200L, "itemUrl"));
        Item item3 = itemService.saveItem(new Item("titleR", 200L, "itemUrl"));
        Item item4 = itemService.saveItem(new Item("titleT", 200L, "itemUrl"));
        Item item5 = itemService.saveItem(new Item("titleY", 200L, "itemUrl"));

        expectedStoredItems.addAll(List.of(item, item1, item2, item3, item4, item5));

        assertEquals(lastId + 1 , item.getId());
        assertEquals(lastId + 2 ,item1.getId());
        assertEquals(lastId + 3 ,item2.getId());
        assertEquals(lastId + 4 ,item3.getId());
        assertEquals(lastId + 5 ,item4.getId());
        assertEquals(lastId + 6 ,item5.getId());
    }

    @Test
    void saveItemWithNonUniqueTitle() {
        assertThrows(DataIntegrityViolationException.class,() ->
                itemService.saveItem(new Item(storedItem.getTitle(), storedItem.getPrice(), storedItem.getImageUrl())));
        assertDoesNotThrow(() -> itemService.saveItem(new Item("uniqueTitle", 100L, "itemUrl")));
    }

    @Test
    void getAllItems() {
        Page<Item> allItems = itemService.getAllItems(PageRequest.of(9, 3));
        assertEquals(expectedTotalItems, allItems.getTotalElements());
        assertEquals(expectedTotalPages, allItems.getTotalPages());
    }

}