package com.scala.controlfinan.controller.transactions;

import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.service.transactions.CreateTransactions;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
public class PostCreateTransaction extends TransactionsRootController{

    private CreateTransactions createTransactions;

    @ApiOperation("Create a Transaction")
    @PostMapping(value = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Transaction> createNewTransaction(@Valid @RequestBody Transaction data) {
        Transaction transactionCreated = createTransactions.process(data);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(transactionCreated.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(transactionCreated);
    }

}
