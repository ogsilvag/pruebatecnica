package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPost {
  @JsonProperty("account_number")
  private String accountNumber;
  @JsonProperty("account_type")
  private String accountType;
  @JsonProperty("initial_balance")
  private double initialBalance;
  @JsonProperty("client_id")
  private String clientId;
}
