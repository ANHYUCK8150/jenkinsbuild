package com.ttukttak.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ttukttak.book.dto.BookInfoDto;
import com.ttukttak.book.dto.BookSearchResponse;
import com.ttukttak.book.dto.MyBookDto;
import com.ttukttak.book.entity.BookInfo;
import com.ttukttak.book.repository.BookInfoRepository;
import com.ttukttak.book.repository.MyBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final MyBookRepository myBookRepository;
	private final BookInfoRepository bookInfoRepository;
	private final InterParkAPIService interParkAPIService;

	@Override
	public Boolean bookInfoSave(String query) {
		List<BookInfoDto> listBook = interParkAPIService.search(query);

		for (BookInfoDto res : listBook) {
			if (!bookInfoRepository.existsByIsbn(res.getIsbn())) {
				BookInfo book = res.toEntity();
				bookInfoRepository.save(book);
			}
		}

		return true;
	}

	@Override
	public List<MyBookDto> findBookList(BookSearchResponse bookSearchResponse) {
		return myBookRepository.findAll()
			.stream()
			.map(c -> new MyBookDto(c))
			.collect(Collectors.toList());
	}
}
