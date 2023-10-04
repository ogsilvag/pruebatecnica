package com.silva.pruebatecnica.controllers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silva.pruebatecnica.models.AccountType;
import lombok.Data;

@Data
public class OperationResponse {
  @JsonProperty("Fecha")
  private String date;
  @JsonProperty("Numero Cuenta")
  private String accountNumber;
  @JsonProperty("Tipo")
  private AccountType accountType;
  @JsonProperty("Saldo Inicial")
  private Double initialBalance;
  @JsonProperty("Estado")
  private Boolean state;
  @JsonProperty("Movimiento")
  private Double amount;
  @JsonProperty("Saldo Disponible")
  private Double actualBalance;
  @JsonProperty("Cliente")
  private String client;
}
