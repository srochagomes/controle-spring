package com.scala.controlfinan.service.accounts;

import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.data.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteAccounts {

    private Accounts accounts;


    public void process(Integer id){
        Optional.ofNullable(id).orElseThrow(()-> new BadRequestException("Id Account is required."));
        AccountEntity accountEntity = accounts.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Account not found."));

        accounts.delete(accountEntity);
    }
}
