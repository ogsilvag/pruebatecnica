package com.silva.pruebatecnica.services.impl;

import com.silva.pruebatecnica.controllers.dto.request.AccountPost;
import com.silva.pruebatecnica.controllers.dto.request.AccountPut;
import com.silva.pruebatecnica.mapper.ClientMapper;
import com.silva.pruebatecnica.models.Account;
import com.silva.pruebatecnica.repository.AccountRepository;
import com.silva.pruebatecnica.repository.entity.AccountEntity;
import com.silva.pruebatecnica.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository repository;
  private final ClientMapper mapper;

  @Override
  public Account getById(String id) {
    var entity = repository.findById(id).orElseThrow();
    return mapper.toAccount(entity);
  }

  @Override
  public Account save(AccountPost account) {
    var entity = mapper.toAccountEntity(account);
    entity.setActualBalance(entity.getInitialBalance());

    if (!repository.existsByAccountNumber(entity.getAccountNumber())) {
      repository.save(entity);
    } else {
      entity=repository.findByAccountNumber(entity.getAccountNumber());
    }

    return mapper.toAccount(entity);
  }

  @Override
  public List<Account> saveAll(List<AccountPost> accounts) {
    var entities = mapper.toAccountEntities(accounts);
    var retorno = new ArrayList<AccountEntity>();
    entities.forEach(e -> {
      e.setState(true);
      e.setActualBalance(e.getInitialBalance());
      if (!repository.existsByAccountNumber(e.getAccountNumber())) {
        repository.save(e);
        retorno.add(e);
      } else {
        var eAux = repository.findByAccountNumber(e.getAccountNumber());
        retorno.add(eAux);
      }
    });
    return mapper.toAccounts(retorno);
  }

  @Override
  public Account disabled(String id) {
    var entity = repository.findById(id).orElseThrow();
    repository.save(entity);
    return mapper.toAccount(entity);
  }

  @Override
  public Account update(String id, AccountPut account) {
    var entity = repository.findById(id).orElseThrow();
    mapper.toAccountEntityTarget(account, entity);
    repository.save(entity);
    return mapper.toAccount(entity);
  }
 }
