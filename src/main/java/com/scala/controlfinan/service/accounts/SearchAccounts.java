package com.scala.controlfinan.service.accounts;

import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.specification.Filter;
import com.scala.controlfinan.repository.specification.QueryOperator;
import com.scala.controlfinan.repository.specification.SpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchAccounts {

    private Accounts accounts;


    public Account findById(Integer id){

        Optional.ofNullable(id).orElseThrow(()-> new BadRequestException("Id Account is required."));
        return Account.parser(
                accounts.findById(id)
                        .orElseThrow(()-> new NotFoundException("Id Account not found.")));
    }


    public Page<Account> findAll(Account account, Pageable data){
        Page<AccountEntity> page = accounts.findAll(this.makeSpec(account), data);
        if (page.getTotalElements()==0){
            throw new NotFoundException("Search Account was not found.");
        }
        return new PageImpl<>(page.get().map(Account::parser).collect(Collectors.toList()),data,page.getTotalElements());
    }


    private Specification<AccountEntity> makeSpec(Account account){
        List<Filter> filters = new ArrayList<>();
        SpecificationBuilder<AccountEntity> specAccount = new SpecificationBuilder<>(AccountEntity.class);
        if (Objects.nonNull(account.getUserId())){
            filters.add(Filter.builder()
                    .field("user.id")
                    .operator(QueryOperator.EQUAL)
                    .value(String.valueOf(account.getUserId()))
                    .build());
        }

        return specAccount.getSpecificationFromFilters(filters);
    }
}
