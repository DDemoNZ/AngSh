package sh.demo.models.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class OrderResponseDto {

    private String userId;
    private Collection<Item> orderItems;
    private String price;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Collection<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<Item> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
