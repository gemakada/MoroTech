DROP TABLE IF EXISTS rating;
CREATE TABLE rating(
     ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     BOOK_ID INTEGER NOT NULL,
     RATING INTEGER,
     COMMENT TEXT
);