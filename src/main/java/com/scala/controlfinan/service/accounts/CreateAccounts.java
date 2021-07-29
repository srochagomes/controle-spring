package com.scala.controlfinan.service.accounts;

import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.data.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateAccounts {


    private Accounts accounts;
    private Users users;

    @Transactional
    public Account process(Account data) {
        Optional.ofNullable(data).orElseThrow(()-> new NotFoundException("Main Object is null."));
        Optional.ofNullable(data.getUserId()).orElseThrow(()-> new BadRequestException("Id user is required."));
        Optional.ofNullable(data.getBalance()).orElseThrow(()-> new BadRequestException("Balance is required."));
        Optional.ofNullable(data.getIncome()).orElseThrow(()-> new BadRequestException("Income is required."));
        Optional.ofNullable(data.getExpense()).orElseThrow(()-> new BadRequestException("Expense is required."));
        UserEntity userEntity = users.findById(data.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        AccountEntity accountEntity = AccountEntity.builder()
                .user(userEntity)
                .balance(data.getBalance())
                .expense(data.getExpense())
                .income(data.getIncome()).build();

        accounts.save(accountEntity);

        data.setId(accountEntity.getId());

        return data;
    }
}
