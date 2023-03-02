package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transaction;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        //Getting book and card object
        Book book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        Card card = cardRepository.findById(issueBookRequestDto.getCardId()).get();

        //Creating transaction object
        Transaction transaction = new Transaction();
        //Setting attributes
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        //Validations
        if(book == null || book.isIssued()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book not available");
        }
        if(card == null || card.getCardStatus() != CardStatus.ACTIVE) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card not valid!");
        }
        //Validations done
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        //Update book attributes
        book.setIssued(true);
        List<Transaction> transactionListOfBook = book.getTransactionList();
        transactionListOfBook.add(transaction);
        book.setTransactionList(transactionListOfBook);
            //Add book in card issuedBookList
        List<Book> issuedBookList = card.getBooksIssued();
        issuedBookList.add(book);
        card.setBooksIssued(issuedBookList);
        //Update card attributes
        List<Transaction> transactionListOfCard = card.getTransactionList();
        transactionListOfCard.add(transaction);
        card.setTransactionList(transactionListOfCard);

        //Now the final part
        cardRepository.save(card);
        //Transaction & Book will be saved automatically by Cascading effect

        return "Book issued Successfully";
    }

    public String getTransactionInfo(Integer bookId, Integer cardId) {
        List<Transaction> transactionList = transactionRepository.getTransactionForBookAndCard(bookId,cardId);
        return transactionList.get(0).getTransactionId();
    }
}
