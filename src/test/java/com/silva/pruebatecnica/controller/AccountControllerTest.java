package com.silva.pruebatecnica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silva.pruebatecnica.controllers.dto.request.AccountPatch;
import com.silva.pruebatecnica.controllers.dto.request.AccountPost;
import com.silva.pruebatecnica.controllers.dto.request.AccountPut;
import com.silva.pruebatecnica.models.Account;
import com.silva.pruebatecnica.models.AccountType;
import com.silva.pruebatecnica.services.AccountService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {
  @Autowired
  private MockMvc mvc;
  @MockBean
  private AccountService service;

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private ModelMapper mapper2;

  @Test
  void obtener() throws Exception {
    var account = creaAccount();
    when(service.getById(anyString()))
        .thenReturn(account);

    mvc
        .perform(
            get("/cuentas/1")
                .content(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void crear() throws Exception {
    var account = creaAccount();
    var accountPost = mapper2.map(account, AccountPost.class);
    //accountPost.setInitialBalance(RandomStringUtils.random(3, true, true));
    when(service.save(any(AccountPost.class)))
        .thenReturn(account);

    mvc
        .perform(
            post("/cuentas/")
                .content(mapper.writeValueAsString(accountPost))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void crearVarios() throws Exception {
    var accountPost = List.of(
        creaAccountPost(),
        creaAccountPost(),
        creaAccountPost()
    );
    var accounts = accountPost
        .stream()
        .map(x -> mapper2.map(x, Account.class))
        .collect(Collectors.toList());

    when(service.saveAll(anyList()))
        .thenReturn(accounts);

    mvc
        .perform(
            post("/cuentas/save")
                .content(mapper.writeValueAsString(accounts))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void modifica() throws Exception {
    var account = creaAccount();
    var accountPut = creaAccountPut();

    mapper2.map(accountPut, account);
    when(service.update(anyString(), any(AccountPut.class)))
        .thenReturn(account);

    mvc
        .perform(
            put("/cuentas/1")
                .content(mapper.writeValueAsString(accountPut))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  private Account creaAccount() {
    return Account
        .builder()
        .id(RandomStringUtils.random(2, true, true))
        .accountNumber(RandomStringUtils.random(10, false, true))
        .accountType(AccountType.AHORROS)
        .state(RandomStringUtils.random(10, true, true))
        .initialBalance(0.0)
        .clientId(RandomStringUtils.random(10, true, true))
        .build();
  }

  private AccountPost creaAccountPost() {
    var account = creaAccount();
    var result = mapper2.map(account, AccountPost.class);
    //result.setPassword(RandomStringUtils.random(10, true, true));
    return result;
  }

  private AccountPut creaAccountPut() {
    var account = creaAccount();
    return mapper2.map(account, AccountPut.class);
  }

  private AccountPatch creaAccountPatch() {
    var account = creaAccount();
    account.setAccountType(AccountType.CORRIENTE);
    return mapper2.map(account, AccountPatch.class);
  }
}
