package com.silva.pruebatecnica.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation")
public class OperationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Enumerated(EnumType.STRING)
  private OperationType operationType;
  private double amount;
  private Date date;
  private Double actualBalance;
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "accountId")
  private AccountEntity account;
}
