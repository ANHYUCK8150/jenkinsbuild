package com.ttukttak.book.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ttukttak.book.entity.Book;
import com.ttukttak.book.entity.Book.BookStatus;
import com.ttukttak.book.entity.Book.DeleteStatus;

public interface BookRepository extends JpaRepository<Book, Long> {

	Page<Book> findByStatusInAndIsDeleteAndTownIdIn(List<BookStatus> bookStatus, DeleteStatus n, List<Long> townIdList,
		PageRequest pageRequest);

	Page<Book> findByStatusInAndIsDeleteAndSubjectContainsAndTownIdIn(List<BookStatus> bookStatus, DeleteStatus n,
		String query, List<Long> townIdList, PageRequest pageRequest);

	Page<Book> findByStatusInAndIsDeleteAndSubjectContainsAndTownIdInAndBookCategoryId(List<BookStatus> bookStatus,
		DeleteStatus n, String query, List<Long> townIdList, Long categoryId, PageRequest pageRequest);

}