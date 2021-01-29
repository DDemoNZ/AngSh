package sh.demo.models.dto;

import lombok.Data;
import sh.demo.models.Item;

@Data
public class ItemResponseDto {

    private Item item;
    private String exception;

    public ItemResponseDto(Item item, String exception) {
        this.item = item;
        this.exception = exception;
    }
}
