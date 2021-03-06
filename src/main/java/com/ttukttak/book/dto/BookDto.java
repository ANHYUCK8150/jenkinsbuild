package com.ttukttak.book.dto;

import java.util.List;

import com.ttukttak.address.dto.TownDto;
import com.ttukttak.book.entity.Book;
import com.ttukttak.book.entity.Book.BookGrade;
import com.ttukttak.book.entity.BookCategory;
import com.ttukttak.book.entity.BookImage;
import com.ttukttak.book.entity.BookInfo;
import com.ttukttak.book.entity.BookReview;
import com.ttukttak.oauth.dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
	private Long id;
	private String subject;
	private String content;
	private BookGrade grade;
	private int deposit;
	private UserDto owner;
	private BookInfo bookInfo;
	private BookCategory bookCategory;
	private TownDto bookTown;
	private List<BookReview> review;

	private BookImage thumbnail;
	private List<BookImage> imageUrls;

	public BookDto(Book book) {
		this.id = book.getId();
		this.subject = book.getSubject();
		this.content = book.getContent();
		this.deposit = book.getDeposit();
		this.grade = book.getGrade();
		this.owner = new UserDto(book.getOwner());
		this.bookInfo = book.getBookInfo();
		this.bookCategory = book.getBookCategory();
		this.bookTown = new TownDto(book.getTown());
		this.thumbnail = book.getThumbnail();
		this.imageUrls = book.getImages();
		this.review = book.getBookReview();
	}

}
