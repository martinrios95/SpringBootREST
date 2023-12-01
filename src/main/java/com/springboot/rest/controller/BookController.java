package com.springboot.rest.controller;

import com.springboot.rest.dto.BookDTO;
import com.springboot.rest.response.ServiceResponse;
import com.springboot.rest.dao.BookDAO;
import com.springboot.rest.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private BookDAO dao;
    @Autowired
    public BookController(BookDAO dao){
        this.dao = dao;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(dao.findAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> get(@PathVariable("id") String id){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        serviceResponse.setCode(HttpStatus.OK.value());
        serviceResponse.setData(boxBook.get());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<ServiceResponse> add(@RequestBody BookDTO bookDTO){
        boolean wasAdded = dao.add(bookDTO);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (!wasAdded){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't add book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> update(@PathVariable("id") String id, @RequestBody BookDTO bookDTO){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        boolean wasAdded = dao.update(id, bookDTO);

        if (!wasAdded){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't update book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> delete(@PathVariable String id){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        boolean wasDeleted = dao.delete(id);

        if (!wasDeleted){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't delete book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
