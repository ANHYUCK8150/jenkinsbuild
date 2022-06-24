package com.ttukttak.book.service;

import java.util.List;

import com.ttukttak.book.dto.BookSearchResponse;
import com.ttukttak.book.dto.MyBookDto;

public interface BookService {

	Boolean bookInfoSave(String query);

	List<MyBookDto> findBookList(BookSearchResponse bookSearchResponse);

}
