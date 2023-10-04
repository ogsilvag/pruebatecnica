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
public class Client {
  @JsonProperty("id")
  private String id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("address")
  private String address;
  @JsonProperty("phone_number")
  private String phoneNumber;
  @JsonProperty("password")
  private String password;
  @JsonProperty("state")
  private Boolean state;
}
