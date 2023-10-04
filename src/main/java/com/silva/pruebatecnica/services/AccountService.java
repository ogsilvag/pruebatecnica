package com.silva.pruebatecnica.services;

import com.silva.pruebatecnica.controllers.dto.request.*;
import com.silva.pruebatecnica.models.Account;

import java.util.List;

public interface AccountService {
  Account getById(String id);

  Account save(AccountPost account);

  List<Account> saveAll(List<AccountPost> accounts);

  Account disabled(String id);

  Account update(String id, AccountPut account);
}
