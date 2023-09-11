package com.redmath.assignment.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Query(value = "SELECT * FROM Balance t WHERE t.account_id = :accountId", nativeQuery = true)
    Optional<Balance> findByAccountId(@Param("accountId") Long accountId);

}
