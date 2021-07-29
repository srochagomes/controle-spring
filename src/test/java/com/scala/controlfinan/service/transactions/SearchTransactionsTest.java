package com.scala.controlfinan.service.transactions;

import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.Transactions;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.data.CategoryEntity;
import com.scala.controlfinan.repository.data.TransactionEntity;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SearchTransactionsTest {

    @Mock
    private Users users;

    @Mock
    private Transactions transactions;

    @Mock
    private Categories categories;

    private SearchTransactions searchTransactions;


    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        searchTransactions = new SearchTransactions(transactions);

    }

    @Test
    @DisplayName("Test when Pass Null Transaction id Should Throw NotFound exception")
    public void whenPassNullAccountShouldThrowException() {
        Integer id = null;
        var exception = assertThrows(BadRequestException.class, () -> searchTransactions.findById(id));
        assertEquals("Id Transaction is required.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id transaction an not found Should Throw NotFound exception")
    public void whenPassIdTransactionAndNotFoundShouldThrowException() {
        Integer id = 5;
        var exception = assertThrows(NotFoundException.class, () -> searchTransactions.findById(id));
        assertEquals("Id Transaction not found.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id transaction and found")
    public void whenPassIdAccountAndFound() {
        Integer id = 5;
        TransactionEntity transactionParameter = TransactionEntity.builder()
                .id(5)
                .createdAt(LocalDateTime.now())
                .value(BigDecimal.ONE)
                .category(CategoryEntity.builder().build())
                .account(AccountEntity.builder().build())
                .build();
        when(transactions.findById(any())).thenReturn(Optional.of(transactionParameter));
        Transaction transactionFound = searchTransactions.findById(id);
        assertNotNull(transactionFound);

        assertEquals(id,transactionFound.getId());

    }

    @Test
    @DisplayName("Test when Search all NotFound exception")
    public void whenSearchNotFoundShouldThrowException() {
        Integer id = 5;
        Page<TransactionEntity> page = Mockito.mock(Page.class);
        Pageable pageable = Mockito.mock(Pageable.class);

        when(transactions.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(0l);
        var exception = assertThrows(NotFoundException.class,
                () -> searchTransactions.findAll(Transaction.builder().build(),pageable));

        assertEquals("Search Transaction was not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Search all and exists")
    public void whenSearchFoundShouldThrowException() {
        Integer id = 5;
        Page<TransactionEntity> page = Mockito.mock(Page.class);

        when(transactions.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(1l);
        Pageable pageable = Mockito.mock(Pageable.class);
        Stream<TransactionEntity> transactionEntities = Stream.of(TransactionEntity.builder()
                .id(5)
                .createdAt(LocalDateTime.now())
                .value(BigDecimal.ONE)
                .category(CategoryEntity.builder().build())
                .account(AccountEntity.builder().build())
                .build());
        when(page.get()).thenReturn(transactionEntities);

        Page<Transaction> all = searchTransactions.findAll(Transaction.builder().build(), pageable);

        assertNotNull(all);
        assertEquals(1,all.getContent().size());
    }


}