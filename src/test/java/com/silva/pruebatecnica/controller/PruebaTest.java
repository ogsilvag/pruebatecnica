package com.silva.pruebatecnica.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silva.pruebatecnica.controllers.dto.request.AccountPost;
import com.silva.pruebatecnica.controllers.dto.request.ClientPost;
import com.silva.pruebatecnica.controllers.dto.request.OperationPost;
import com.silva.pruebatecnica.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PruebaTest {
  @Autowired
  private MockMvc mvc;
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  void pruebaTecnica() throws Exception {
    //crea usuarios
    var usuario1 = ClientPost
        .builder()
        .name("José")
        .lastName("Lema")
        .address("Óvalo s/n y Principal")
        .phoneNumber("0982254785")
        .password("1234")
        .build();
    var usuario2 = ClientPost
        .builder()
        .name("Marianela")
        .lastName("Montalvo")
        .address("Amazonas y NNUU")
        .phoneNumber("097548965")
        .password("5678")
        .build();
    var usuario3 = ClientPost
        .builder()
        .name("Juan")
        .lastName("Osorio")
        .address("13 junio y Equinoccial")
        .phoneNumber("098874587")
        .password("1245")
        .build();
    var response1 = mvc
        .perform(
            post("/clientes/save")
                .content(mapper.writeValueAsString(
                    List.of(usuario1, usuario2, usuario3)
                ))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();

    Class<?> clz = Class.forName(Client.class.getName());
    JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clz);
    List<Client> usuarios = mapper.readValue(response1.getResponse().getContentAsString(), type);

//creación de Cuentas
    var account1 = AccountPost
        .builder()
        .accountNumber("478758")
        .accountType(AccountType.AHORROS.toString())
        .initialBalance(2000.00)
        .clientId(usuarios.get(0).getId())
        .build();
    var account2 = AccountPost
        .builder()
        .accountNumber("225487")
        .accountType(AccountType.CORRIENTE.toString())
        .initialBalance(100.00)
        .clientId(usuarios.get(1).getId())
        .build();
    var account3 = AccountPost
        .builder()
        .accountNumber("495878")
        .accountType(AccountType.AHORROS.toString())
        .initialBalance(0.00)
        .clientId(usuarios.get(2).getId())
        .build();
    var account4 = AccountPost
        .builder()
        .accountNumber("496825")
        .accountType(AccountType.AHORROS.toString())
        .initialBalance(540.00)
        .clientId(usuarios.get(1).getId())
        .build();

    var response2 = mvc
        .perform(
            post("/cuentas/save")
                .content(mapper.writeValueAsString(
                    List.of(account1, account2, account3, account4)
                ))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();

    clz = Class.forName(Account.class.getName());
    type = mapper.getTypeFactory().constructCollectionType(List.class, clz);
    List<Account> cuentas = mapper.readValue(response2.getResponse().getContentAsString(), type);

    var account5 = AccountPost
        .builder()
        .accountNumber("585545")
        .accountType(AccountType.CORRIENTE.toString())
        .initialBalance(1000.00)
        .clientId(usuarios.get(0).getId())
        .build();

  var response3 = mvc
      .perform(
          post("/cuentas/")
              .content(mapper.writeValueAsString(account5))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andReturn();

    Account cuenta = mapper.readValue(response3.getResponse().getContentAsString(), Account.class);
//operaciones
  var mov1 = OperationPost
      .builder()
      .accountNumber("478758")
      .operationType(OperationType.RETIRO)
      .amount(575)
      .build();
  var mov2=OperationPost
      .builder()
      .accountNumber("225487")
      .operationType(OperationType.DEPOSITO)
      .amount(600)
      .build();
    var mov3 = OperationPost
        .builder()
        .accountNumber("495878")
        .operationType(OperationType.DEPOSITO)
        .amount(150)
        .build();
    var mov4=OperationPost
        .builder()
        .accountNumber("496825")
        .operationType(OperationType.RETIRO)
        .amount(540)
        .build();
    var response4 = mvc
        .perform(
            post("/movimientos/")
                .content(mapper.writeValueAsString(
                    List.of(mov1,mov2,mov3,mov4)
                ))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();

    var response5 = mvc
        .perform(
            get("/movimientos/report")
                .param("clientId",usuarios.get(1).getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

}
