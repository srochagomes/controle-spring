package com.scala.controlfinan.controller.accounts;

import com.scala.controlfinan.config.GetAccountsParameterConfig;
import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.service.accounts.SearchAccounts;
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

@AllArgsConstructor
@RestController
public class GetAccount extends AccountsRootController{

    private SearchAccounts searchAccounts;

    @GetMapping(value = "v1/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search account by id")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(searchAccounts.findById(id));
    }

    @GetMapping(value = "v1", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search All accounts")
    @GetAccountsParameterConfig
    public ResponseEntity<Page<Account>> getAllAccount(
            @PageableDefault(sort = { "id"},page = 0, size = 10) Pageable pageable,
            @RequestParam(value = "userId",required = false) Integer userId
            ) {

        Account accountFilter = Account.builder().userId(userId).build();

        return ResponseEntity.ok(searchAccounts.findAll(accountFilter,pageable));
    }


}
