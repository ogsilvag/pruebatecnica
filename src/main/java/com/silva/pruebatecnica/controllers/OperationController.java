package com.silva.pruebatecnica.controllers;

import com.silva.pruebatecnica.controllers.dto.response.OperationResponse;
import com.silva.pruebatecnica.models.Operation;
import com.silva.pruebatecnica.controllers.dto.request.OperationPost;
import com.silva.pruebatecnica.services.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movimientos")
public class OperationController {
  private final OperationService service;

  @GetMapping("/{accountId}/{id}")
  public Operation getById(
      @PathVariable(name = "accountId") String accountId,
      @PathVariable(name = "id") String id
  ) {
    return service.getById(accountId,id);
  }

  @PostMapping("/")
  public List<Operation> save(
      @Valid @RequestBody List<OperationPost> operations
  ) {
    return service.save(operations);
  }
  @GetMapping("/report")
  public List<OperationResponse> report(
      @RequestParam(name = "clientId") String clientId
  ){
    return service.report(clientId);
  }
}