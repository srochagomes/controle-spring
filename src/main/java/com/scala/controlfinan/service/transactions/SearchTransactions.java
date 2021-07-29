package com.scala.controlfinan.service.transactions;

import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.repository.Transactions;
import com.scala.controlfinan.repository.data.TransactionEntity;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchTransactions {

    private Transactions transactions;


    public Transaction findById(Integer id){
        Optional.ofNullable(id).orElseThrow(()-> new BadRequestException("Id Transaction is required."));
        return Transaction.parser(
                transactions.findById(id)
                        .orElseThrow(()-> new NotFoundException("Id Transaction not found.")));
    }


    public Page<Transaction> findAll(Transaction transaction, Pageable data){
        Page<TransactionEntity> page = transactions.findAll(this.makeSpec(transaction), data);
        if (page.getTotalElements()==0){
            throw new NotFoundException("Search Transaction was not found.");
        }
        return new PageImpl<>(page.get().map(Transaction::parser).collect(Collectors.toList()),data,page.getTotalElements());
    }


    private Specification<TransactionEntity> makeSpec(Transaction transaction){
        List<Filter> filters = new ArrayList<>();
        SpecificationBuilder<TransactionEntity> specTransaction = new SpecificationBuilder<>(TransactionEntity.class);

        if (Objects.nonNull(transaction.getAccountId())){
            filters.add(Filter.builder()
                    .field("account.id")
                    .operator(QueryOperator.EQUAL)
                    .value(String.valueOf(transaction.getAccountId()))
                    .build());
        }

        filters.add(Filter.builder()
                .field("createdAt")
                .operator(QueryOperator.BETWEEN_DATE)
                .values(Arrays.asList(transaction.getStartedAt(),
                        transaction.getEndedAt()))
                .build());


        return specTransaction.getSpecificationFromFilters(filters);
    }
}
