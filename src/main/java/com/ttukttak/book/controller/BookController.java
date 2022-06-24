package com.ttukttak.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttukttak.book.dto.BookInfoDto;
import com.ttukttak.book.dto.BookSearchResponse;
import com.ttukttak.book.dto.MyBookDto;
import com.ttukttak.book.service.BookService;
import com.ttukttak.book.service.InterParkAPIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
	private final InterParkAPIService interParkAPIService;
	private final BookService bookService;

	@GetMapping("/search")
	public ResponseEntity<List<BookInfoDto>> search(@RequestParam
	String query) {
		List<BookInfoDto> searchResult = interParkAPIService.search(query);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(searchResult);
	}

	/*
	 * 검색 도서 DB insert 벌크 데이터 만들기
	 */
	@PostMapping("/search/insert")
	public ResponseEntity<Boolean> searchIns(@RequestParam
	String query) {

		Boolean chk = bookService.bookInfoSave(query);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(chk);
	}

	/*
	 * Book 등록
	 */
	@PostMapping("/register")
	public ResponseEntity<Boolean> setBook(MyBookDto myBookDto) {

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(null);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getMyBookList(BookSearchResponse bookSearchResponse) {
		List<MyBookDto> bookList = bookService.findBookList(bookSearchResponse);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(bookList);
	}

}
