package sh.demo.models.dto;


import java.util.ArrayList;
import java.util.Collection;

public class ItemPage {

    private Collection<Item> items;
    private String message;

    public ItemPage(Collection<Item> items, String message) {
        this.items = items;
        this.message = message;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
