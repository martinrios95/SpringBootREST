package com.springboot.rest.mapper;

import com.springboot.rest.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getString("id"));
        book.setName(rs.getString("name"));
        book.setPrice(rs.getFloat("price"));
        book.setCategory(rs.getString("category"));
        book.setAuthor(rs.getString("author"));

        return book;
    }
}
