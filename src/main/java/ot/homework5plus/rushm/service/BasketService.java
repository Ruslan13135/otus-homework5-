package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Basket;

public interface BasketService {

    Basket getBasket(Long basketId);

    Basket addBook(Long basketId, Long bookId);

    Basket removeBook(Long basketId, Long bookId);

    Basket clearBasket(Long basketId);
}
