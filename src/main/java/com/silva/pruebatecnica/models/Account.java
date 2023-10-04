package com.silva.pruebatecnica.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @JsonProperty("id")
  private String id;
  @JsonProperty("account_number")
  private String accountNumber;
  @JsonProperty("account_type")
  private AccountType accountType;
  @JsonProperty("initial_balance")
  private Double initialBalance;
  @JsonProperty("state")
  private String state;
  @JsonProperty("clientId")
  private String clientId;
}
