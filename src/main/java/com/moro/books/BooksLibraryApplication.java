/*
 * @(#)BooksLibraryApplication.java
 */
package com.moro.books;

import org.komamitsu.spring.data.sqlite.EnableSqliteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableSqliteRepositories
@EnableCaching
public class BooksLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksLibraryApplication.class, args);
    }
}
