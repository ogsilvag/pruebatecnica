package com.silva.pruebatecnica.services.impl;

import com.silva.pruebatecnica.controllers.dto.request.OperationPost;
import com.silva.pruebatecnica.controllers.dto.response.OperationResponse;
import com.silva.pruebatecnica.mapper.ClientMapper;
import com.silva.pruebatecnica.models.Operation;
import com.silva.pruebatecnica.models.OperationType;
import com.silva.pruebatecnica.repository.AccountRepository;
import com.silva.pruebatecnica.repository.ClientRepository;
import com.silva.pruebatecnica.repository.OperationRepository;
import com.silva.pruebatecnica.repository.entity.OperationEntity;
import com.silva.pruebatecnica.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
  private final OperationRepository repository;
  private final ClientMapper mapper;
  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;

  @Override
  public Operation getById(String accountId, String id) {
    var entity = repository.getByAccountIdAndId(accountId, id).orElseThrow();
    return mapper.toOperation(entity);
  }

  @Override
  public List<Operation> save(List<OperationPost> operations) {
    var entities = new ArrayList<OperationEntity>();
    operations.forEach(o->{
      var entity = mapper.toOperationEntity(o);
      var account = accountRepository.findByAccountNumber(o.getAccountNumber());
      entity.setAccount(account);
      entity.setDate(new Date());

      var factor=o.getOperationType().equals(OperationType.DEPOSITO)?1:-1;
      var actualBalance=account.getActualBalance()+factor*o.getAmount();
      if (actualBalance>=0){
        account.setActualBalance(actualBalance);
        entity.setActualBalance(actualBalance);

        accountRepository.save(account);
        repository.save(entity);

        entities.add(entity);
      }
    });
    return mapper.toOperations(entities);
  }

  @Override
  public List<OperationResponse> report(String clientId) {
var operations= repository.findByAccount_ClientId(clientId);
return mapper.toOperationsResponse(operations);
  }
}
