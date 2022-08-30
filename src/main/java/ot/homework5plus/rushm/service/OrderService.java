package ot.homework5plus.rushm.service;


import ot.homework5plus.rushm.models.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order getOrder(Long orderId);

    List<Order> getOrders(List<String> orderIds);

    Order addOrder(Order order);

    Order createByBasketId(Long basketId);

    Order updateOrder(Order order);

    void deleteOrder(Long orderId);

    long getCount();
}
