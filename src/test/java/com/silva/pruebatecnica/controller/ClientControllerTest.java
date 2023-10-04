package com.silva.pruebatecnica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.silva.pruebatecnica.controllers.dto.request.ClientPatch;
import com.silva.pruebatecnica.controllers.dto.request.ClientPost;
import com.silva.pruebatecnica.controllers.dto.request.ClientPut;
import com.silva.pruebatecnica.models.Client;
import com.silva.pruebatecnica.services.ClientService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
class ClientControllerTest {
  @Autowired
  private MockMvc mvc;
  @MockBean
  private ClientService service;

  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private ModelMapper mapper2;

  @BeforeAll
  private void beforeAll() {
    mapper2
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.LOOSE);
    mapper2.createTypeMap(Client.class, ClientPost.class);

    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  @Test
  void obtiene() throws Exception {
    var client = creaCliente();

    when(service.getById(anyString()))
        .thenReturn(client);

    mvc
        .perform(
            get("/clientes/1")
                .content(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void crear() throws Exception {
    var cliente = creaCliente();
    var clientePost = mapper2.map(cliente, ClientPost.class);
    clientePost.setPassword(RandomStringUtils.random(10, true, true));
    when(service.save(any(ClientPost.class)))
        .thenReturn(cliente);

    mvc
        .perform(
            post("/clientes/")
                .content(mapper.writeValueAsString(clientePost))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void crearVarios() throws Exception {
    var clientsPost = List.of(
        creaClientePost(),
        creaClientePost(),
        creaClientePost()
    );
    var clients = clientsPost
        .stream()
        .map(x -> mapper2.map(x, Client.class))
        .collect(Collectors.toList());

    when(service.saveAll(anyList()))
        .thenReturn(clients);

    mvc
        .perform(
            post("/clientes/save")
                .content(mapper.writeValueAsString(clientsPost))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void modifica() throws Exception {
    var client = creaCliente();
    var clientPut = creaClientePut();

    mapper2.map(clientPut, client);
    when(service.update(anyString(), any(ClientPut.class)))
        .thenReturn(client);

    mvc
        .perform(
            put("/clientes/1")
                .content(mapper.writeValueAsString(clientPut))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  @Test
  void altera() throws Exception {
    var client = creaCliente();
    var clientPatch = creaClientePatch();
    mapper2.map(clientPatch, client);

    when(service.alter(anyString(), any(ClientPatch.class)))
        .thenReturn(client);
    mvc
        .perform(
            patch("/clientes/1")
                .content(mapper.writeValueAsString(clientPatch))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();
  }

  private Client creaCliente() {
    return Client
        .builder()
        .id(RandomStringUtils.random(2, true, true))
        .address(RandomStringUtils.random(10, true, true))
        .name(RandomStringUtils.random(10, true, true))
        .address(RandomStringUtils.random(10, true, true))
        .state(true)
        .phoneNumber(RandomStringUtils.random(10, true, true))
        .lastName(RandomStringUtils.random(10, true, true))
        .name(RandomStringUtils.random(10, true, true))
        .build();
  }

  private ClientPost creaClientePost() {
    var client = creaCliente();
    var result = mapper2.map(client, ClientPost.class);
    result.setPassword(RandomStringUtils.random(10, true, true));
    return result;
  }

  private ClientPut creaClientePut() {
    var client = creaCliente();
    return mapper2.map(client, ClientPut.class);
  }

  private ClientPatch creaClientePatch() {
    var client = creaCliente();
    return mapper2.map(client, ClientPatch.class);
  }
}
