package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPost {
  @JsonProperty(value = "name", required = true)
  @NotNull
  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("address")
  private String address;
  @JsonProperty("phone_number")
  private String phoneNumber;
  @NotNull
  @NotEmpty
  @JsonProperty("password")
  private String password;
}
