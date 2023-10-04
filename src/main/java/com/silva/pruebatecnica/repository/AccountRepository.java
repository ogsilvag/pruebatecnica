package com.silva.pruebatecnica.repository;

import com.silva.pruebatecnica.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends
    JpaRepository<AccountEntity, String> {
  @Query("select (count(a) > 0) from AccountEntity a where a.accountNumber = ?1")
  boolean existsByAccountNumber(String accountNumber);
  AccountEntity findByAccountNumber(String accountNumber);

}
