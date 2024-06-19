package com.springboot.rest.controller;

import com.springboot.rest.dto.BookDTO;
import com.springboot.rest.response.ListServiceResponse;
import com.springboot.rest.response.ServiceResponse;
import com.springboot.rest.dao.BookDAO;
import com.springboot.rest.models.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Tag(name="Books")
@RequestMapping("/books")
public class BookController {
    @Autowired
    protected BookDAO dao;

    @GetMapping()
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(dao.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id){
        Optional<Book> boxBook = dao.find(id);

        if (boxBook.isEmpty()){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        return ResponseEntity.ok(boxBook.get());
    }

    @GetMapping("/name/{partialName}")
    public ResponseEntity<?> getByName(@PathVariable("partialName") String partialName){
        if (partialName.isBlank()){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.BAD_REQUEST.value(), "Name is required!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        List<Book> books = dao.findByNameContaining(partialName.trim());
        ListServiceResponse<Book> listServiceResponse = new ListServiceResponse<>(HttpStatus.OK.value(), books, null);

        return ResponseEntity.ok(listServiceResponse);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody BookDTO bookDTO){
        Book book = new Book();

        UUID uuid = UUID.randomUUID();

        book.setId(uuid.toString());
        book.setName(bookDTO.getName());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());

        boolean wasAdded = dao.add(book);

        if (!wasAdded){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.BAD_REQUEST.value(), "Couldn't add book!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        return ResponseEntity.ok(ServiceResponse.ok());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody BookDTO bookDTO){
        Optional<Book> boxBook = dao.find(id);

        if (boxBook.isEmpty()){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        Book book = boxBook.get();

        book.setName(bookDTO.getName());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());

        boolean wasUpdated = dao.update(id, book);

        if (!wasUpdated){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.BAD_REQUEST.value(), "Couldn't update book!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        return ResponseEntity.ok(ServiceResponse.ok());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Optional<Book> boxBook = dao.find(id);

        if (boxBook.isEmpty()){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        boolean wasDeleted = dao.delete(id);

        if (!wasDeleted){
            ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.BAD_REQUEST.value(), "Couldn't delete book!");
            return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
        }

        return ResponseEntity.ok(ServiceResponse.ok());
    }
}