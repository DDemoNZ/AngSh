package sh.demo.service;

import sh.demo.models.dto.Item;
import sh.demo.models.dto.OrderResponseDto;
import sh.demo.models.dto.Orders;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    Orders addOrder(OrderResponseDto order);

    Collection<Orders> getUserOrders(String id);
}
