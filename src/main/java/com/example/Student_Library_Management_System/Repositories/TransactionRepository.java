package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query(value = "select * from transaction where book_is=:bookId and card_id=:cardId and is_issue_operation=true",nativeQuery = true)
    List<Transaction> getTransactionForBookAndCard(int bookId, int cardId);
}
