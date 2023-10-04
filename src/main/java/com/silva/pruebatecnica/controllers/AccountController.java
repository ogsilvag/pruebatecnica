package com.silva.pruebatecnica.controllers;

import com.silva.pruebatecnica.controllers.dto.request.AccountPost;
import com.silva.pruebatecnica.controllers.dto.request.AccountPut;
import com.silva.pruebatecnica.models.Account;
import com.silva.pruebatecnica.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cuentas")
public class AccountController {
  private final AccountService service;

  @GetMapping("/{id}")
  public Account getById(
      @PathVariable(value = "id") String id) {
    return service.getById(id);
  }

  @PostMapping("/")
  public Account save(
      @Valid @RequestBody AccountPost account
  ) {
    return service.save(account);
  }

  @PostMapping("/save")
  public List<Account> saveAll(
      @RequestBody List<AccountPost> accounts
  ) {
    return service.saveAll(accounts);
  }

  @PutMapping("/{id}")
  public Account update(
      @PathVariable(name = "id") String id,
      @RequestBody AccountPut account
  ) {
    return service.update(id, account);
  }

  @DeleteMapping("/{id}")
  public Account disabled(
      @PathVariable(value = "id") String id
  ) {
    return service.disabled(id);
  }
}
