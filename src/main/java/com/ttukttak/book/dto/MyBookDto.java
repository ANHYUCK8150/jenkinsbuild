package com.ttukttak.book.dto;

import com.ttukttak.book.entity.BookCategory;
import com.ttukttak.book.entity.BookInfo;
import com.ttukttak.book.entity.BookStatus;
import com.ttukttak.book.entity.MyBook;
import com.ttukttak.oauth.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyBookDto {
	private Long id;
	private String subject;
	private String content;
	private BookStatus status;
	private User user;
	private BookInfo bookInfo;
	private BookCategory bookCategory;

	public MyBookDto(MyBook myBook) {
		this.id = myBook.getId();
		this.subject = myBook.getSubject();
		this.content = myBook.getContent();
		this.status = myBook.getStatus();
		this.user = myBook.getUser();
		this.bookInfo = myBook.getBookInfo();
		this.bookCategory = myBook.getBookCategory();
	}

}
