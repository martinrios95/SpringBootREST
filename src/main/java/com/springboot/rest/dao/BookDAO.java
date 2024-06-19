package com.springboot.rest.dao;

import com.springboot.rest.mapper.BookMapper;
import com.springboot.rest.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDAO implements DAO<Book, String> {
    @Autowired
    private JdbcTemplate connection;

    private BookMapper mapper;

    public BookDAO(JdbcTemplate connection) {
        this.connection = connection;
        this.mapper = new BookMapper();
    }

    @Override
    public Optional<Book> find(String id) {
        try {
            Book book = connection.queryForObject("SELECT * FROM Books WHERE id = ?", mapper, id);

            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return connection.query("SELECT * FROM Books", mapper);
    }

    public List<Book> findByNameContaining(String name) {
        // SQLi-prone??
        return connection.query("SELECT * FROM Books WHERE name LIKE '" + name + "%'", mapper);
    }

    @Override
    public boolean add(Book book){
        try {
            int rows = connection.update("INSERT INTO Books VALUES (?, ?, ?, ?, ?)", book.getId(), book.getName(), book.getPrice(), book.getCategory(), book.getAuthor());

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean update(String id, Book book){
        try {
            int rows = connection.update("UPDATE Books SET name = ?, price = ?, category = ?, author = ? WHERE id = ?", book.getName(), book.getPrice(), book.getCategory(), book.getAuthor(), id);

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            int rows = connection.update("DELETE FROM Books WHERE id = ?", id);

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
