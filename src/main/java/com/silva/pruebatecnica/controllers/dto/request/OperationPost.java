package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silva.pruebatecnica.models.OperationType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationPost {
  @JsonProperty("account_number")
  @NotNull
  @NotEmpty
  private String accountNumber;
  @JsonProperty("operation_type")
  @NotNull
  @NotEmpty
  private OperationType operationType;
  @JsonProperty("amount")
  @NotNull
  @NotEmpty
  @Min(value=0)
  private Integer amount;
}
