package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.DTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.DTOs.BookResponseDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public String createAuthor(AuthorEntryDto authorEntryDto) {
        Author author = new Author();
        //Assign values to the author attributes(Rule 1)
        author.setName(authorEntryDto.getName());
        author.setAge(authorEntryDto.getAge());
        author.setCountry(authorEntryDto.getCountry());
        author.setRating(authorEntryDto.getRating());
        //Now save the author into the repo
        authorRepository.save(author);
        return "Author added successfully";
    }

    public AuthorResponseDto getAuthor(Integer authorId) {
        Author author = authorRepository.findById(authorId).get();

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setName(author.getName());
        authorResponseDto.setAge(author.getAge());
        authorResponseDto.setCountry(author.getCountry());
        authorResponseDto.setRating(author.getRating());
        //Now to set the list of booksWritten by the author
        //Create a list<BookResponseDto>
        List<Book> bookList = author.getBooksWritten();
        List<BookResponseDto> booksWrittenDto = new ArrayList<>();
        for(Book book : bookList) {
            BookResponseDto bookResponseDto = new BookResponseDto();
            //Setting attributes
            bookResponseDto.setName(book.getName());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setPages(book.getPages());
            //Adding to booksWrittenDto list
            booksWrittenDto.add(bookResponseDto);
        }
        authorResponseDto.setBooksWritten(booksWrittenDto);
        return authorResponseDto;
    }
}
