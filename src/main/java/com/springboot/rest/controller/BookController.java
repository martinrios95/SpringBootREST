package com.springboot.rest.controller;

import com.springboot.rest.dto.BookDTO;
import com.springboot.rest.response.ServiceResponse;
import com.springboot.rest.dao.BookDAO;
import com.springboot.rest.models.Book;
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
@RequestMapping("/books")
public class BookController {
    private BookDAO dao;
    @Autowired
    public BookController(BookDAO dao){
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(dao.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        serviceResponse.setCode(HttpStatus.OK.value());
        serviceResponse.setData(boxBook.get());

        return ResponseEntity.ok(boxBook);
    }

    @PostMapping()
    public ResponseEntity<ServiceResponse> add(@RequestBody BookDTO bookDTO){
        Book book = new Book();

        UUID uuid = UUID.randomUUID();

        book.setId(uuid.toString());
        book.setName(bookDTO.getName());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());

        boolean wasAdded = dao.add(book);

        ServiceResponse serviceResponse = new ServiceResponse<>();

        if (!wasAdded){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't add book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return ResponseEntity.ok(serviceResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable("id") String id, @RequestBody BookDTO bookDTO){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        Book book = boxBook.get();

        book.setName(bookDTO.getName());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());

        boolean wasAdded = dao.update(id, book);

        if (!wasAdded){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't update book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return ResponseEntity.ok(serviceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable String id){
        Optional<Book> boxBook = dao.find(id);

        ServiceResponse serviceResponse = new ServiceResponse<>();

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

        return ResponseEntity.ok(serviceResponse);
    }
}
