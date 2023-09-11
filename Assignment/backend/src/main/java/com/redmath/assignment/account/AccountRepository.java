package com.redmath.assignment.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByNameAndPassword(String name, String password);
    @Query(value = "SELECT * FROM Account a WHERE a.name = :username", nativeQuery = true)
    Account findByName(String username);
}