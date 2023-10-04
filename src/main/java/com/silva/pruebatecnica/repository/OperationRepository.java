package com.silva.pruebatecnica.repository;

import com.silva.pruebatecnica.repository.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends
    JpaRepository<OperationEntity, String> {
  List<OperationEntity> findByAccountId(String accountId);

  Optional<OperationEntity> getByAccountIdAndId(String accountId, String id);
List<OperationEntity> findByAccount_ClientId (String clientId);
}
