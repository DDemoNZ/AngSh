package sh.demo.service;

import sh.demo.models.dto.OrderResponseDto;
import sh.demo.models.Orders;

import java.util.Collection;

public interface OrderService {

    Orders addOrder(OrderResponseDto order);

    Collection<Orders> getUserOrders(String id);

}
