package com.scala.controlfinan.controller.accounts;

import com.scala.controlfinan.service.accounts.DeleteAccounts;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DeleteAccount extends AccountsRootController{

    private DeleteAccounts deleteAccountsAccounts;

    @ApiOperation("Delete an account")
    @DeleteMapping(value = "/v1/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteNewAccount(@PathVariable("id") Integer id) {
        deleteAccountsAccounts.process(id);
        return ResponseEntity.noContent().build();
    }


}
