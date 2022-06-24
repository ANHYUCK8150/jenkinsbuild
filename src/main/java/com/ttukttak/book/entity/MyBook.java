package com.ttukttak.book.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ttukttak.book.dto.MyBookDto;
import com.ttukttak.common.BaseTimeEntity;
import com.ttukttak.oauth.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyBook extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private BookStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_info_id")
	private BookInfo bookInfo;

	@ManyToOne
	@JoinColumn(name = "book_category_id")
	private BookCategory bookCategory;

	@Builder
	public MyBook(Long id, String subject, String content, BookStatus status, User user, BookInfo bookInfo,
		BookCategory bookCategory) {

		this.id = id;
		this.subject = subject;
		this.content = content;
		this.status = status;
		this.user = user;
		this.bookInfo = bookInfo;
		this.bookCategory = bookCategory;
	}

	public static MyBook of(MyBookDto myBookDto) {
		return MyBook.builder()
			.id(myBookDto.getId())
			.subject(myBookDto.getSubject())
			.content(myBookDto.getContent())
			.status(myBookDto.getStatus())
			.user(myBookDto.getUser())
			.bookInfo(myBookDto.getBookInfo())
			.bookCategory(myBookDto.getBookCategory())
			.build();
	}

}
