package com.silva.pruebatecnica.mapper;

import com.silva.pruebatecnica.controllers.dto.request.*;
import com.silva.pruebatecnica.controllers.dto.response.OperationResponse;
import com.silva.pruebatecnica.models.Account;
import com.silva.pruebatecnica.models.Client;
import com.silva.pruebatecnica.models.Operation;
import com.silva.pruebatecnica.repository.entity.AccountEntity;
import com.silva.pruebatecnica.repository.entity.ClientEntity;
import com.silva.pruebatecnica.repository.entity.OperationEntity;
import com.silva.pruebatecnica.repository.entity.OperationType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  //region Client
  Client toClient(ClientEntity clientEntity);

  List<Client> toClients(List<ClientEntity> entities);

  ClientEntity toClientEntity(Client client);

  ClientEntity toClientEntity(ClientPost client);

  List<ClientEntity> toClientEntities(List<ClientPost> clients);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toClientEntityTarget(ClientPut client, @MappingTarget ClientEntity entity);

  //endregion
  //region Account
  Account toAccount(AccountEntity entity);

  List<Account> toAccounts(List<AccountEntity> entities);

  default ClientEntity createClientEntity(String clientId) {
    return ClientEntity.builder().id(clientId).build();
  }

  @Mapping(target = "client", expression = "java(createClientEntity(account.getClientId()))")
  AccountEntity toAccountEntity(AccountPost account);

  List<AccountEntity> toAccountEntities(List<AccountPost> accounts);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toAccountEntityTarget(AccountPut account, @MappingTarget AccountEntity entity);

  //endregion
  @Named("statusToString")
  default String statusToString(Boolean state) {
    if (state == null) {
      return "Inactivo";
    }
    return state ? "Activo" : "Inactivo";
  }

  @Named("clientToString")
  default String clientToString(ClientEntity entity) {
    return entity.getName();
  }

  @Named("clientInstance")
  default ClientEntity clientInstance(String clientId) {
    return ClientEntity.builder().id(clientId).build();
  }

  //region Operations
  Operation toOperation(OperationEntity entity);

  List<Operation> toOperations(List<OperationEntity> entities);


  //@Mapping(source = "accountId", target = "account.id")
  OperationEntity toOperationEntity(OperationPost operation);

  List<OperationEntity> toOperationEntities(List<OperationPost> operation);

  //endregion
  @Named("accountInstance")
  default AccountEntity accountInstance(String accountId) {
    return AccountEntity.builder().id(accountId).build();
  }

  default Double realAmount(OperationEntity entity) {
    var factor = entity.getOperationType().equals(OperationType.DEPOSITO) ? 1 : -1;
    return factor * entity.getAmount();
  }

  @Mapping(source = "date", target = "date", dateFormat = "dd/MM/yyyyy")
  @Mapping(source = "account.accountNumber", target = "accountNumber")
  @Mapping(source = "account.accountType", target = "accountType")
  @Mapping(source = "account.initialBalance", target = "initialBalance")
  @Mapping(source = "account.state", target = "state")
  @Mapping(target = "amount", expression = "java(realAmount(entity))")
  @Mapping(target = "actualBalance", source = "actualBalance")
  @Mapping(target = "client", expression = "java(fullName(entity.getAccount().getClient()))")
  OperationResponse toOperationResponse(OperationEntity entity);

  default String fullName(ClientEntity client) {
    return client.getName() + " " + client.getLastName();
  }

  List<OperationResponse> toOperationsResponse(List<OperationEntity> operations);


}
