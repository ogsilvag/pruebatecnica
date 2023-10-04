package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silva.pruebatecnica.models.AccountType;
import lombok.Data;

@Data
public class AccountPatch {
  @JsonProperty("account_type")
  private AccountType accountType;
}
