package com.baxi.wallet.transactions.repo;

import com.app.tory.transactions.model.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRecordRepo extends JpaRepository<TransactionRecord, String> {

    @Query(value = "SELECT * FROM transaction_record t WHERE t.completed=?1 and t.customerId = ?2", nativeQuery = true)
    List<TransactionRecord> findByCompletedAndCustomerId(boolean completed, long customerId);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.completed = ?1", nativeQuery = true)
    List<TransactionRecord> findByCompleted(boolean completed);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.ticketReference = ?1", nativeQuery = true)
    List<TransactionRecord> findByTicketReference(String ticketReference);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.customerId = ?1", nativeQuery = true)
    List<TransactionRecord> findByCustomerId(long customerId);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.businessId = ?1", nativeQuery = true)
    List<TransactionRecord> findByBusinessId(long businessId);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.sessionRef = ?1", nativeQuery = true)
    List<TransactionRecord> findBySessionRef(String sessionRef);

    @Query(value = "SELECT * FROM transaction_record t WHERE t.Transaction_reference = ?1", nativeQuery = true)
    TransactionRecord findByTransactionReference(String transactionRef);

    @Query(value = "Select * from transaction_record t where t.transaction_date < DATEADD(MINUTE, -30, GETDATE()) and t.completed=false and t.cancelled=false", nativeQuery = true)
    List<TransactionRecord> findTransactionPendingOver30Minutes();


}
