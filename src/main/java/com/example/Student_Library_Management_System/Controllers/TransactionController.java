package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/issueBook")
    public String issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto) {
        try {
            return transactionService.issueBook(issueBookRequestDto);
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/getTxnId")
    public String getTransactionInfo(@RequestParam("bookId") Integer bookId, @RequestParam("cardId") Integer cardId) {
        return transactionService.getTransactionInfo(bookId,cardId);
    }
}