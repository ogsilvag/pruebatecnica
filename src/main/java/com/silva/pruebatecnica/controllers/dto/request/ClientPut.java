package com.silva.pruebatecnica.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientPut {
  @JsonProperty("name")
  private String name;
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("address")
  private String address;
  @JsonProperty("phone_number")
  private String phoneNumber;
}
