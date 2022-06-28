package ot.homework5plus.rushm.repository;

import ot.homework5plus.rushm.domain.Book;

public interface BookRepositoryCustom {
    Book findBookByCommentId(long Id);
}
