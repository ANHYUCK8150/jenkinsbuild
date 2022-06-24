package com.ttukttak.book.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ttukttak.common.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class BookInfo extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "DATE")
	private Date publishedDate;

	private int price;

	private String image;

	private String publisher;

	private String author;

	private String isbn;

	@Builder
	public BookInfo(Long id, String name, String description, Date publishedDate, int price, String image,
		String publisher, String author, String isbn) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.publishedDate = publishedDate;
		this.price = price;
		this.image = image;
		this.publisher = publisher;
		this.author = author;
		this.isbn = isbn;
	}

	public static BookInfo of(BookInfo bookInfo) {
		return BookInfo.builder()
			.name(bookInfo.getName())
			.description(bookInfo.getDescription())
			.publishedDate(bookInfo.getPublishedDate())
			.price(bookInfo.getPrice())
			.image(bookInfo.getImage())
			.publisher(bookInfo.getPublisher())
			.author(bookInfo.getAuthor())
			.isbn(bookInfo.getIsbn())
			.build();
	}
}