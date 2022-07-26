package ot.homework5plus.rushm.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.service.BookRepositoryService;
import ot.homework5plus.rushm.service.CachedDataService;

import java.util.List;

@Service
@AllArgsConstructor
public class BookRepositoryServiceImpl implements BookRepositoryService {
    private final BookRepository bookRepository;
    private final CachedDataService cachedDataService;

    @HystrixCommand(groupKey = "BookRepo", fallbackMethod = "getCachedBooks", commandKey = "findAllBooks")
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();

    }

    @HystrixCommand(groupKey = "BookRepo", fallbackMethod = "getCachedBookById", commandKey = "findBookById")
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteBookById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    private List<Book> getCachedBooks() {
        return cachedDataService.getCachedBooks();
    }

    private Book getCachedBookById(Long id) {
        return cachedDataService.getCachedBook();
    }
}
