package com.scala.controlfinan.repository.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = true)
    private AccountEntity account;

    @Column(name = "transaction_value")
    private BigDecimal value;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = true)
    private CategoryEntity category;

}
