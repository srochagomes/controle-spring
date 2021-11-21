package com.scala.controlfinan.service.transactions;

import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.Transactions;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.data.CategoryEntity;
import com.scala.controlfinan.repository.data.TransactionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateTransactions {

    private Transactions transactions;
    private Accounts accounts;
    private Categories categories;

    @Transactional
    public Transaction process(Transaction data) {
        Optional.ofNullable(data).orElseThrow(()-> new NotFoundException("Main Object is null."));
        Optional.ofNullable(data.getAccountId()).orElseThrow(()-> new BadRequestException("Account id is required."));
        Optional.ofNullable(data.getCategoryId()).orElseThrow(()-> new BadRequestException("Category id is required."));
        Optional.ofNullable(data.getValue()).orElseThrow(()-> new BadRequestException("Value is required."));
        AccountEntity accountFound = accounts.findById(data.getAccountId())
                .orElseThrow(()-> new NotFoundException("Account not found."));

        CategoryEntity categoryFound = categories.findById(data.getCategoryId())
                .orElseThrow(()-> new NotFoundException("Category not found."));

        TransactionEntity transactionNew = TransactionEntity.builder()
                                            .account(accountFound)
                                            .category(categoryFound)
                                            .value(data.getValue())
                                            .createdAt(LocalDateTime.now())
                                            .build();
        transactions.save(transactionNew);
        accountFound.processTransaction(transactionNew);
        accounts.save(accountFound);

        return Transaction.builder()
                .id(transactionNew.getId())
                .categoryId(transactionNew.getCategory().getId())
                .accountId(transactionNew.getAccount().getId())
                .value(transactionNew.getValue()).build();
    }
}
