package com.scala.controlfinan.controller.accounts;

import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.service.accounts.CreateAccounts;
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
public class PostCreateAccount extends AccountsRootController{

    private CreateAccounts createAccounts;

    @ApiOperation("Create an account")
    @PostMapping(value = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Account> createNewAccount(@Valid @RequestBody Account data) {
        Account accountCreated = createAccounts.process(data);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(accountCreated.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(accountCreated);
    }


}
