/*
 * @(#)BooksLibraryApplication.java
 */
package com.moro.books;

import org.komamitsu.spring.data.sqlite.EnableSqliteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSqliteRepositories
public class BooksLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksLibraryApplication.class, args);
    }
}
