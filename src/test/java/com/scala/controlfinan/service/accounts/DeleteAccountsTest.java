package com.scala.controlfinan.service.accounts;

import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.service.accounts.DeleteAccounts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DeleteAccountsTest {
    @Mock
    private Users users;

    @Mock
    private Accounts accounts;

    private com.scala.controlfinan.service.accounts.DeleteAccounts DeleteAccounts;


    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        DeleteAccounts = new DeleteAccounts(accounts);

    }


    @Test
    @DisplayName("Test when Pass Null id account Should Throw NotFound exception")
    public void whenPassNullAccountShouldThrowException() {
        Integer id = null;
        var exception = assertThrows(BadRequestException.class, () -> DeleteAccounts.process(id));
        assertEquals("Id Account is required.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id account an not found Should Throw NotFound exception")
    public void whenPassIdAccountAndNotFoundShouldThrowException() {
        Integer id = 5;
        var exception = assertThrows(NotFoundException.class, () -> DeleteAccounts.process(id));
        assertEquals("Id Account not found.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id account an found Should Throw NotFound exception")
    public void whenPassIdAccountAndFoundShouldThrowException() {
        Integer id = 5;
        AccountEntity accountParameter = AccountEntity.builder()
                .id(5)
                .expense(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();
        when(accounts.findById(any())).thenReturn(Optional.of(accountParameter));
        DeleteAccounts.process(id);
        then(accounts).should(times(1)).findById(any());
        then(accounts).should(times(1)).delete(any());

    }

}
