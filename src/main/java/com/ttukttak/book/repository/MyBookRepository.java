package com.ttukttak.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttukttak.book.entity.MyBook;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

}
