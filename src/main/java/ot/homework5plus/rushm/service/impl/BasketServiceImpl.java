package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Basket;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.repositories.BasketRepository;
import ot.homework5plus.rushm.repositories.BookRepository;
import ot.homework5plus.rushm.service.BasketService;
import ot.homework5plus.rushm.service.UserService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final UserService userService;

    public BasketServiceImpl(
        BasketRepository basketRepository,
        BookRepository bookRepository,
        UserService userService
    ) {
        this.basketRepository = basketRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Basket getBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (Objects.isNull(basket)) {
            basket = createBasket();
        }
        return basket;
    }

    @Override
    @Transactional
    public Basket addBook(Long basketId, Long bookId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (Objects.isNull(basket)) {
            basket = createBasket();
        }
        Book book = bookRepository.findById(bookId).orElse(null);
        if (Objects.nonNull(basket) && Objects.nonNull(book)) {
            List<Book> books = basket.getBooks();
            if (!books.contains(book)) {
                books.add(book);
            }
            basket.setBooks(books);
            basketRepository.save(basket);
        }
        return basket;
    }

    @Override
    @Transactional
    public Basket removeBook(Long basketId, Long bookId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (Objects.isNull(basket)) {
            basket = createBasket();
        }
        if (Objects.nonNull(basket)) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (Objects.nonNull(book)) {
                basket.getBooks().remove(book);
                basketRepository.save(basket);
            }
        }
        return basket;
    }

    @Override
    @Transactional
    public Basket clearBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (Objects.isNull(basket)) {
            basket = createBasket();
            return basket;
        }
        basket.getBooks().removeAll(basket.getBooks());
        return basketRepository.save(basket);
    }

    @Transactional
    public Basket createBasket() {
        Basket basket = null;
        String username = userService.getCurrentUser().orElse(null);
        User user = userService.findByUsername(username);
        if (Objects.nonNull(user)) {
            basket = basketRepository.save(new Basket(user.getId(), user, List.of()));
        }
        return basket;
    }
}
