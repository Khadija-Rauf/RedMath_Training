package com.redmath.assignment.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM Transaction t WHERE t.account_id = :accountId", nativeQuery = true)
    Optional<List<Transaction>>  findByAccountId(@Param("accountId") Long accountId);
}
