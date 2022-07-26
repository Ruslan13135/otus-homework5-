package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CachedDataService {

    List<Book> getCachedBooks();

    Book getCachedBook();

    List<Comment> getCachedComments();
}
