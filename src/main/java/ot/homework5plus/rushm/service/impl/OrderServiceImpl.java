package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.exceptions.BadRequestException;
import ot.homework5plus.rushm.models.entity.Basket;
import ot.homework5plus.rushm.models.entity.Order;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.models.enums.OrderStatus;
import ot.homework5plus.rushm.repositories.BookRepository;
import ot.homework5plus.rushm.repositories.OrderRepository;
import ot.homework5plus.rushm.repositories.UserRepository;
import ot.homework5plus.rushm.service.BasketService;
import ot.homework5plus.rushm.service.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final BasketService basketService;
    private final UserRepository userRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            BookRepository bookRepository,
            BasketService basketService,
            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.basketService = basketService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        log.debug("The application got in method - getAll()");
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        log.debug("The application got in method - getOrder");
        return orderRepository.getOne(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrders(List<String> orderIds) {
        log.debug("The application got in method - getOrders");
        List<Long> ids = orderIds.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
        return orderRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        log.debug("The application got in method - addOrder");
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order createByBasketId(Long basketId) {
        log.debug("The application got in method - createByBasketId");
        Basket basket = basketService.getBasket(basketId);
        if (basket.getBooks().isEmpty()) {
            throw new BadRequestException("Basket is empty, order not created");
        }
        User user = userRepository.findById(basketId).orElse(null);
        Order order = new Order(
            null,
            getOrderNumber(),
            LocalDateTime.now(),
            OrderStatus.OPEN,
            user,
            basket.getBooks()
        );
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        log.debug("The application got in method - updateOrder");
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        log.debug("The application got in method - deleteOrder");
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount");
        return orderRepository.count();
    }

    private String getOrderNumber() {
        String date = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());
        String rnd = RandomStringUtils.randomAlphabetic(10);
        return String.format("O-%s-%s", date, rnd);
    }
}
