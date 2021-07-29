package com.scala.controlfinan.service.accounts;


import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.UserEntity;
import com.scala.controlfinan.service.accounts.CreateAccounts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CreateAccountsTest {

    @Mock
    private Users users;

    @Mock
    private Accounts accounts;

    private CreateAccounts createAccounts;


    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        createAccounts = new CreateAccounts(accounts,users);

    }


    @Test
    @DisplayName("Test when Pass Null Account Should Throw NotFound exception")
    public void whenPassNullAccountShouldThrowException(){
        Account account = null;
        NotFoundException exception = assertThrows(NotFoundException.class, () -> createAccounts.process(account));
        assertEquals("Main Object is null.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Id user Should Throw Bad exception")
    public void whenPassNullIdUserShouldThrowException(){
        Account account = Account.builder().build();
        var exception = assertThrows(BadRequestException.class, () -> createAccounts.process(account));
        assertEquals("Id user is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Balance Should Throw Bad exception")
    public void whenPassNullBalanceShouldThrowException(){
        Account account = Account.builder()
                .userId(1)
                .build();
        var exception = assertThrows(BadRequestException.class, () -> createAccounts.process(account));
        assertEquals("Balance is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Income Should Throw Bad exception")
    public void whenPassNullIncomeShouldThrowException(){
        Account account = Account.builder()
                .userId(1)
                .balance(BigDecimal.ONE)
                .build();
        var exception = assertThrows(BadRequestException.class, () -> createAccounts.process(account));
        assertEquals("Income is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Expense Should Throw Bad exception")
    public void whenPassNullExpenseShouldThrowException(){
        Account account = Account.builder()
                .userId(1)
                .balance(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .build();
        var exception = assertThrows(BadRequestException.class, () -> createAccounts.process(account));
        assertEquals("Expense is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Expense Should Throw Bad exception")
    public void whenPassUserNotFoundThrowException(){
        Account account = Account.builder()
                .userId(1)
                .balance(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .build();
        var exception = assertThrows(NotFoundException.class, () -> createAccounts.process(account));
        assertEquals("User not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass all requirements")
    public void whenPassAllRequiremets(){
        Account account = Account.builder()
                .userId(1)
                .balance(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .build();

        UserEntity userFound = UserEntity.builder().id(1).email("aaa@aaa.com").name("Elis").build();
        when(users.findById(any())).thenReturn(Optional.of(userFound));
        Account accountCreated = createAccounts.process(account);
        then(users).should(times(1)).findById(any());
        then(accounts).should(times(1)).save(any());
        assertNotNull(accountCreated);
    }

}