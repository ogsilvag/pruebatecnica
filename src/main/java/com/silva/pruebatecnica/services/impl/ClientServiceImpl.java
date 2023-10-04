package com.silva.pruebatecnica.services.impl;

import com.silva.pruebatecnica.controllers.dto.request.ClientPatch;
import com.silva.pruebatecnica.controllers.dto.request.ClientPost;
import com.silva.pruebatecnica.controllers.dto.request.ClientPut;
import com.silva.pruebatecnica.mapper.ClientMapper;
import com.silva.pruebatecnica.models.Client;
import com.silva.pruebatecnica.repository.ClientRepository;
import com.silva.pruebatecnica.repository.entity.ClientEntity;
import com.silva.pruebatecnica.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final ClientRepository repository;
  private final ClientMapper mapper;

  @Override
  public Client getById(String id) {
    var entity = repository.findById(id).orElseThrow();
    return mapper.toClient(entity);
  }

  @Override
  public Client save(ClientPost client) {
    var entity = mapper.toClientEntity(client);
    entity.setState(true);
    if (!repository.existsByNameAndLastName(entity.getName(), entity.getLastName())) {
      repository.save(entity);
    } else {
      entity = repository.findByNameAndLastName(entity.getName(), entity.getLastName());
    }
    return mapper.toClient(entity);
  }

  @Override
  public List<Client> saveAll(List<ClientPost> clients) {
    var entities = mapper.toClientEntities(clients);
    var retorno = new ArrayList<ClientEntity>();
    entities.forEach(e -> {
      e.setState(true);
      if (!repository.existsByNameAndLastName(e.getName(), e.getLastName())) {
        repository.save(e);
        retorno.add(e);
      } else {
        var eAux = repository.findByNameAndLastName(e.getName(), e.getLastName());
        retorno.add(eAux);
      }
    });
    return mapper.toClients(retorno);
  }

  @Override
  public Client disabled(String id) {
    var entity = repository.findById(id).orElseThrow();
    entity.setState(false);
    repository.save(entity);
    return mapper.toClient(entity);
  }

  @Override
  public Client update(String id, ClientPut client) {
    var entity = repository.findById(id).orElseThrow();
    mapper.toClientEntityTarget(client, entity);
    repository.save(entity);
    return mapper.toClient(entity);
  }

  @Override
  public Client alter(String id, ClientPatch client) {
    return null;
  }
}
