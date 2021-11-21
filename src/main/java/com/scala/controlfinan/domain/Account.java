package com.scala.controlfinan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.data.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private BigDecimal income;

    @NotNull
    private BigDecimal expense;

    public static Account parser(AccountEntity entity){
        AccountEntity accountEntity = Optional.ofNullable(entity).orElseGet(AccountEntity::new);
        return Account.builder()
                .id(accountEntity.getId())
                .balance(accountEntity.getBalance())
                .expense(accountEntity.getExpense())
                .income(accountEntity.getIncome())
                .userId(Optional.ofNullable(accountEntity.getUser())
                        .orElseGet(UserEntity::new).getId())
                .build();
    }

}
