package sh.demo.models.dto;

import lombok.Data;

@Data
public class ItemResponseDto {

    private Item item;
    private String exception;

    public ItemResponseDto(Item item, String exception) {
        this.item = item;
        this.exception = exception;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
