package sh.demo.controller;

import org.springframework.web.bind.annotation.*;
import sh.demo.models.dto.OrderResponseDto;
import sh.demo.models.Orders;
import sh.demo.service.OrderService;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Orders saveOrder(@RequestBody OrderResponseDto order) {
        Orders orders = orderService.addOrder(order);
        return orders;
    }

    @GetMapping("/{id}")
    public Collection<Orders> getUserOrders(@PathVariable String id) {
        Collection<Orders> userOrders = orderService.getUserOrders(id);
        return userOrders;
    }
}
