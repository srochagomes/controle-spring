package com.scala.controlfinan.controller.transactions;

import com.scala.controlfinan.config.GetTransactionByAccountParameterConfig;
import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.service.transactions.SearchTransactions;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class GetTransaction extends TransactionsRootController {

    private SearchTransactions searchTransactions;

    @GetMapping(value = "v1/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search transaction by id")
    public ResponseEntity<Transaction> getAccount(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(searchTransactions.findById(id));
    }


    @GetMapping(value = "v1/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search All Transaction by account")
    @GetTransactionByAccountParameterConfig
    public ResponseEntity<Page<Transaction>> getAllAccount(
            @PathVariable("id") Integer accountId,
            @RequestParam(value = "startedAt",required = false) String startedAt,
            @RequestParam(value = "endedAt",required = false) String endedAt,
            @PageableDefault(sort = { "id"},page = 0, size = 10)  Pageable pageable) {

        startedAt=this.concatStartHours(startedAt);
        endedAt=this.concatEndHours(endedAt);

        Transaction transactionFilter = Transaction
                           .builder()
                           .accountId(accountId)
                           .startedAt(this.transformDateAndReset(startedAt,"startedAt", LocalDateTime.now().minusDays(1l).truncatedTo(ChronoUnit.DAYS)))
                           .endedAt(this.transformDateAndReset(endedAt,"endedAt",LocalDateTime.now().plusDays(1l).truncatedTo(ChronoUnit.DAYS)))
                           .build();

        return ResponseEntity.ok(searchTransactions.findAll(transactionFilter,pageable));
    }

    private String concatStartHours(String date) {
        if ( Objects.nonNull(date)){
            date=date.concat(" 00:00:00");
        }
        return date;
    }
    private String concatEndHours(String date) {
        if ( Objects.nonNull(date)){
            date=date.concat(" 23:59:59");
        }
        return date;
    }

    private LocalDateTime transformDateAndReset(String date, String field, LocalDateTime defaultDate){
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try{
            String dateTries = Optional.ofNullable(date).orElseGet(() -> defaultDate.format(formatters));
            return  LocalDateTime.parse(dateTries,formatters);
        }catch (Exception e){
            throw new BadRequestException("Formato inválido para ".concat(field),e);
        }
    }
}
