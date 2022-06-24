package com.ttukttak.book.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ttukttak.book.entity.BookInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookInfoDto {
	private String name;
	private String description;
	private String publishedDate;
	private int price;
	private String image;
	private String publisher;
	private String author;
	private String isbn;

	public BookInfoDto(BookInfo bookInfo) {
		this.name = bookInfo.getName();
		this.description = bookInfo.getDescription();
		this.publishedDate = bookInfo.getPublishedDate().toString();
		this.price = bookInfo.getPrice();
		this.image = bookInfo.getImage();
		this.publisher = bookInfo.getPublisher();
		this.author = bookInfo.getAuthor();
		this.isbn = bookInfo.getIsbn();
	}

	public BookInfo toEntity() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = formatter.parse(publishedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return BookInfo.builder()
			.name(name)
			.description(description)
			.publishedDate(date)
			.price(price)
			.image(image)
			.publisher(publisher)
			.author(author)
			.isbn(isbn)
			.build();
	}
}
