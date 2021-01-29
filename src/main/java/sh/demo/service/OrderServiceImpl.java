package sh.demo.service;

import org.springframework.stereotype.Service;
import sh.demo.models.Item;
import sh.demo.models.dto.OrderResponseDto;
import sh.demo.models.Orders;
import sh.demo.models.User;
import sh.demo.repository.OrderJpa;
import sh.demo.repository.UserJpa;

import java.util.Collection;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderJpa orderJpa;
    private final UserJpa userJpa;

    public OrderServiceImpl(OrderJpa orderJpa, UserJpa userJpa) {
        this.orderJpa = orderJpa;
        this.userJpa = userJpa;
    }

    @Override
    public Orders addOrder(OrderResponseDto orderDto) {
        Orders order = new Orders();
        order.setUser(userJpa.getOne(Long.parseLong(orderDto.getUserId())));
        order.setItems(orderDto.getOrderItems());
        order.setPrice(orderDto.getOrderItems().stream().mapToLong(Item::getPrice).sum());
        Orders save = orderJpa.save(order);
        return save;
    }

    @Override
    public Collection<Orders> getUserOrders(String id) {
        User user = userJpa.getOne(Long.parseLong(id));
        return orderJpa.findAllByUser(user);
    }
}
