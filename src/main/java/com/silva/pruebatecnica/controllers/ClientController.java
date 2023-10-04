package com.silva.pruebatecnica.controllers;

import com.silva.pruebatecnica.controllers.dto.request.ClientPatch;
import com.silva.pruebatecnica.controllers.dto.request.ClientPost;
import com.silva.pruebatecnica.controllers.dto.request.ClientPut;
import com.silva.pruebatecnica.models.Client;
import com.silva.pruebatecnica.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClientController {
  private final ClientService service;

  @GetMapping("/{id}")
  public Client getById(
      @PathVariable(value = "id") String id) {
    return service.getById(id);
  }

  @PostMapping("/")
  public Client save(
      @Valid @RequestBody ClientPost client
  ) {
    return service.save(client);
  }

  @PostMapping("/save")
  public List<Client> saveAll(
      @RequestBody List<ClientPost> clients
  ) {
    return service.saveAll(clients);
  }

  @PutMapping("/{id}")
  public Client update(
      @PathVariable(name = "id") String id,
      @RequestBody ClientPut client
  ) {
    return service.update(id, client);
  }

  @PatchMapping("/{id}")
  public Client altera(
      @PathVariable(name = "id") String id,
      @RequestBody ClientPatch client
  ) {
    return service.alter(id, client);
  }

  @DeleteMapping("/{id}")
  public Client disabled(
      @PathVariable(value = "id") String id
  ) {
    return service.disabled(id);
  }
}
