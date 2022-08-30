package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Order;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.models.enums.OrderStatus;
import ot.homework5plus.rushm.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class OrderController {

    private static final String ORDER_SERVICE = "orderService";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Получить все заказы
    @GetMapping("/api/order")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadGetAllOrder", type = Type.SEMAPHORE)
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    // Получить заказ
    @GetMapping("/api/order/{id}")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadGetOrder", type = Type.SEMAPHORE)
    public ResponseEntity<Order> getOrders(@PathVariable("id") Long orderId) {
        if (orderId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.getOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Создать новый заказ на базе корзины
    @PostMapping("/api/order/{basketId}")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadGetOrder", type = Type.SEMAPHORE)
    public ResponseEntity<Order> addOrderByBasketId(@PathVariable("basketId") Long basketId) {
        if (basketId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.createByBasketId(basketId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Создать новый заказ
    @PostMapping("/api/order")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadGetOrder", type = Type.SEMAPHORE)
    public ResponseEntity<Order> addOrder(@RequestBody Order requestOrder) {
        if (requestOrder == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestOrder.setId(null);
        Order order = orderService.addOrder(requestOrder);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Обновить заказ
    @PutMapping("/api/order/{id}")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadGetOrder", type = Type.SEMAPHORE)
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order requestOrder) {
        if (requestOrder == null || requestOrder.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestOrder.setId(id);
        Order order = orderService.getOrder(requestOrder.getId());
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order = orderService.updateOrder(requestOrder);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Удалить заказ
    @DeleteMapping("/api/order/{id}")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadDelOrder", type = Type.SEMAPHORE)
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long orderId) {
        if (orderId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Order>> bulkHeadGetAllOrder(Exception t) {
        log.info("bulkHeadGetAllOrder");
        List<Order> order = new ArrayList<>();
        order.add(new Order(1L, "order", LocalDateTime.now(), OrderStatus.OPEN, new User(), List.of()));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public ResponseEntity<Order> bulkHeadGetOrder(Exception t) {
        log.info("bulkHeadGetOrder");
        Order order = new Order(1L, "order", LocalDateTime.now(), OrderStatus.OPEN, new User(), List.of());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelOrder(Exception t) {
        log.info("bulkHeadDelOrder");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
