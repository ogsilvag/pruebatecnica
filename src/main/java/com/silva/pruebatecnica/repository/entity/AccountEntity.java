package com.silva.pruebatecnica.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class  AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String accountNumber;
  @Enumerated(EnumType.STRING)
  private AccountType accountType;
  private double initialBalance;
  private Boolean state;
  private Double actualBalance;
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "clientId", referencedColumnName = "id")
  private ClientEntity client;
}
