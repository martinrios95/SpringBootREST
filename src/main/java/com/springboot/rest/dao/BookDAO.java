package com.springboot.rest.dao;

import com.springboot.rest.dto.BookDTO;
import com.springboot.rest.mapper.BookMapper;
import com.springboot.rest.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Book> find(String k) {
        try {
            Book book = connection.queryForObject("SELECT * FROM Books WHERE id = ?", mapper, k);

            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return connection.query("SELECT * FROM Books", mapper);
    }

    public boolean add(BookDTO bookDTO){
        try {
            Book book = new Book();

            UUID uuid = UUID.randomUUID();

            book.setId(uuid.toString());
            book.setName(bookDTO.getName());
            book.setPrice(bookDTO.getPrice());
            book.setCategory(bookDTO.getCategory());
            book.setAuthor(bookDTO.getAuthor());

            int rows = connection.update("INSERT INTO Books VALUES (?, ?, ?, ?, ?)", book.getId(), book.getName(), book.getPrice(), book.getCategory(), book.getAuthor());

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean update(String k, BookDTO bookDTO){
        try {
            int rows = connection.update("UPDATE Books SET name = ?, price = ?, category = ?, author = ? WHERE id = ?", bookDTO.getName(), bookDTO.getPrice(), bookDTO.getCategory(), bookDTO.getAuthor(), k);

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String k) {
        try {
            int rows = connection.update("DELETE FROM Books WHERE id = ?", k);

            return rows != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
