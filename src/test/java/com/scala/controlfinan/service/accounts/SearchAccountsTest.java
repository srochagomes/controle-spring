package com.scala.controlfinan.service.accounts;

import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.service.accounts.SearchAccounts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SearchAccountsTest {
    @Mock
    private Users users;

    @Mock
    private Accounts accounts;

    private SearchAccounts searchAccounts;


    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        searchAccounts = new SearchAccounts(accounts);

    }


    @Test
    @DisplayName("Test when Pass Null id account Should Throw NotFound exception")
    public void whenPassNullAccountShouldThrowException() {
        Integer id = null;
        var exception = assertThrows(BadRequestException.class, () -> searchAccounts.findById(id));
        assertEquals("Id Account is required.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id account an not found Should Throw NotFound exception")
    public void whenPassIdAccountAndNotFoundShouldThrowException() {
        Integer id = 5;
        var exception = assertThrows(NotFoundException.class, () -> searchAccounts.findById(id));
        assertEquals("Id Account not found.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id account and found")
    public void whenPassIdAccountAndFound() {
        Integer id = 5;
        AccountEntity accountParameter = AccountEntity.builder()
                .id(5)
                .expense(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();
        when(accounts.findById(any())).thenReturn(Optional.of(accountParameter));
        Account accoundFound = searchAccounts.findById(id);
        assertNotNull(accoundFound);

        assertEquals(id,accoundFound.getId());

    }

    @Test
    @DisplayName("Test when Search all NotFound exception")
    public void whenSearchNotFoundShouldThrowException() {
        Integer id = 5;
        Page<AccountEntity> page = Mockito.mock(Page.class);
        Pageable pageable = Mockito.mock(Pageable.class);

        when(accounts.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(0l);
        var exception = assertThrows(NotFoundException.class, () -> searchAccounts.findAll(Account.builder().build(),pageable));

        assertEquals("Search Account was not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Search all and exists")
    public void whenSearchFoundShouldThrowException() {
        Integer id = 5;
        Page<AccountEntity> page = Mockito.mock(Page.class);

        when(accounts.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(1l);
        Pageable pageable = Mockito.mock(Pageable.class);
        Stream<AccountEntity> accountEntities = Stream.of(AccountEntity.builder()
                .id(5)
                .expense(BigDecimal.ONE)
                .income(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build());
        when(page.get()).thenReturn(accountEntities);

        Page<Account> all = searchAccounts.findAll(Account.builder().build(), pageable);

        assertNotNull(all);
        assertEquals(1,all.getContent().size());
    }

}