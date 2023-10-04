package com.silva.pruebatecnica.repository;

import com.silva.pruebatecnica.repository.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends
    JpaRepository<ClientEntity, String> {
  @Query("select c from ClientEntity c where c.name = ?1 and c.lastName = ?2")
  ClientEntity findByNameAndLastName(String name, String lastName);
  @Query("select (count(c) > 0) from ClientEntity c where c.name = ?1 and c.lastName = ?2")
  boolean existsByNameAndLastName(String name, String lastName);
}
