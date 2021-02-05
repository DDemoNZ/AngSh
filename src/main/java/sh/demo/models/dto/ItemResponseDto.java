package sh.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sh.demo.models.Item;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {

    private Item item;
    private String exception;
}
