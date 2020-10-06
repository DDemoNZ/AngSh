package sh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sh.demo.models.dto.Item;
import sh.demo.models.dto.OrderResponseDto;
import sh.demo.models.dto.Orders;
import sh.demo.service.OrderService;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
//    public Orders saveOrder(Collection<Item> orderItems) {
    public Orders saveOrder(@RequestBody OrderResponseDto order) {
        Orders orders = orderService.addOrder(order);
        return orders;
    }

    @GetMapping("/{id}")
//    @RequestMapping(path = "id={id}" ,method = RequestMethod.GET)
    public Collection<Orders> getUserOrders(@PathVariable String id) {
        Collection<Orders> userOrders = orderService.getUserOrders(id);
        return userOrders;
    }
}


