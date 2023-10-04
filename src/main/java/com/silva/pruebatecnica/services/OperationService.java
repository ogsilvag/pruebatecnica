package com.silva.pruebatecnica.services;

import com.silva.pruebatecnica.controllers.dto.response.OperationResponse;
import com.silva.pruebatecnica.models.Operation;
import com.silva.pruebatecnica.controllers.dto.request.OperationPost;

import java.util.List;

public interface OperationService {
  Operation getById(String accountId, String id);

  List<Operation> save(List<OperationPost> operations);

  List<OperationResponse> report(String clientId);
}
