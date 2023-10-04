package com.silva.pruebatecnica.services;

import com.silva.pruebatecnica.controllers.dto.request.ClientPatch;
import com.silva.pruebatecnica.models.Client;
import com.silva.pruebatecnica.controllers.dto.request.ClientPost;
import com.silva.pruebatecnica.controllers.dto.request.ClientPut;

import java.util.List;

public interface ClientService {
  Client getById(String id);

  Client save(ClientPost client);

  List<Client> saveAll(List<ClientPost> clients);

  Client disabled(String id);

  Client update(String id, ClientPut client);

  Client alter(String id, ClientPatch client);
}
