package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Basket;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.service.BasketService;
import ot.homework5plus.rushm.service.UserService;

import java.util.Objects;

@Slf4j
@RestController
public class BasketController {
    private static final String BASKET_SERVICE = "basketService";

    private final BasketService basketService;
    private final UserService userService;

    public BasketController(
        BasketService basketService,
        UserService userService
    ) {
        this.basketService = basketService;
        this.userService = userService;
    }

    // Получить корзину по id
    @GetMapping("/api/basket")
    @Bulkhead(name = BASKET_SERVICE, fallbackMethod = "bulkHeadGetBasket", type = Type.SEMAPHORE)
    public ResponseEntity<Basket> getBasket() {
        User currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Basket basket = basketService.getBasket(currentUser.getId());
        if (basket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    // Добавить книгу в корзину
    @PostMapping("/api/basket/add/{bookId}")
    @Bulkhead(name = BASKET_SERVICE, fallbackMethod = "bulkHeadGetBasket", type = Type.SEMAPHORE)
    public ResponseEntity<Basket> addBookIntoBasket(@PathVariable("bookId") Long bookId) {
        User currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Basket basket = basketService.addBook(currentUser.getId(), bookId);
        if (basket == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    // Удалить книгу из корзины
    @PostMapping("/api/basket/remove/{bookId}")
    @Bulkhead(name = BASKET_SERVICE, fallbackMethod = "bulkHeadGetBasket", type = Type.SEMAPHORE)
    public ResponseEntity<Basket> removeBookFromBasket(@PathVariable("bookId") Long bookId) {
        User currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Basket basket = basketService.removeBook(currentUser.getId(), bookId);
        if (basket == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    // Очистить корзину
    @DeleteMapping("/api/basket")
    @Bulkhead(name = BASKET_SERVICE, fallbackMethod = "bulkHeadDelBasket", type = Type.SEMAPHORE)
    public ResponseEntity<Basket> clearBasket() {
        User currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Basket basket = basketService.clearBasket(currentUser.getId());
        if (basket == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    public ResponseEntity<Basket> bulkHeadGetBasket(Exception t) {
        log.info("bulkHeadGetBasket");
        Basket author = new Basket();
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelBasket(Exception t) {
        log.info("bulkHeadDelBasket");
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private User getCurrentUser() {
        String currentUserLogin = userService.getCurrentUser().orElse(null);
        if (Objects.isNull(currentUserLogin)) {
            return null;
        }
        return userService.findByUsername(currentUserLogin);
    }
}
