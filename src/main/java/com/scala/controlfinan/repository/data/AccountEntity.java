package com.scala.controlfinan.repository.data;


import com.scala.controlfinan.domain.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    private UserEntity user;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "expense")
    private BigDecimal expense;

    public void processTransaction(TransactionEntity transaction) {
        CategoryEntity category = transaction.getCategory();
        CategoryType type = category.getType();
        this.balance = this.balance.add(type.calculateBalance(transaction.getValue()));
        this.income  = this.income.add(type.calculateIncome(transaction.getValue()));
        this.expense = this.expense.add(type.calculateExpense(transaction.getValue()));
    }
}
