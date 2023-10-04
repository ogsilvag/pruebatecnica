package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silva.pruebatecnica.models.AccountType;

public class AccountPut {
  @JsonProperty("account_number")
  private String accountNumber;
  @JsonProperty("account_type")
  private AccountType accountType;
  @JsonProperty("initial_balance")
  private double initialBalance;
  @JsonProperty("client_id")
  private String clientId;
}
