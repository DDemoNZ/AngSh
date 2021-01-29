package sh.demo.models.dto;

import lombok.Data;
import sh.demo.models.Item;

import java.util.Collection;

@Data
public class OrderResponseDto {

    private String userId;
    private Collection<Item> orderItems;
    private String price;
}
