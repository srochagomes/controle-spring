package com.scala.controlfinan.service.transactions;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.domain.CategoryType;
import com.scala.controlfinan.domain.Transaction;
import com.scala.controlfinan.repository.Accounts;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.Transactions;
import com.scala.controlfinan.repository.data.AccountEntity;
import com.scala.controlfinan.repository.data.CategoryEntity;
import com.scala.controlfinan.service.categories.CreateCategories;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CreateTransactionTest {

    @Mock
    private Transactions transactions;

    @Mock
    private Accounts accounts;

    @Mock
    private Categories categories;

    private CreateTransactions createTransactions;

    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        createTransactions = new CreateTransactions(transactions,accounts,categories);

    }

    @Test
    @DisplayName("Test when Pass Null Transaction Should Throw NotFound exception")
    public void whenPassNullTransactionShouldThrowException(){
        Transaction transaction = null;
        var exception = assertThrows(NotFoundException.class, () -> createTransactions.process(transaction));
        assertEquals("Main Object is null.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Id Account Should Throw NotFound exception")
    public void whenPassNullIdAccountShouldThrowException(){
        Transaction transaction = Transaction.builder().build();
        var exception = assertThrows(BadRequestException.class, () -> createTransactions.process(transaction));
        assertEquals("Account id is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Id Category Should Throw NotFound exception")
    public void whenPassNullIdCategoryShouldThrowException(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .build();
        var exception = assertThrows(BadRequestException.class, () -> createTransactions.process(transaction));
        assertEquals("Category id is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null value Should Throw NotFound exception")
    public void whenPassNullValueShouldThrowException(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .categoryId(1)
                .build();
        var exception = assertThrows(BadRequestException.class, () -> createTransactions.process(transaction));
        assertEquals("Value is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass account id Should Throw NotFound exception")
    public void whenPassAccountIdValueShouldThrowException(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .categoryId(1)
                .value(BigDecimal.ONE)
                .build();

        var exception = assertThrows(NotFoundException.class, () -> createTransactions.process(transaction));
        assertEquals("Account not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass category id Should Throw NotFound exception")
    public void whenPassCategoryIdValueShouldThrowException(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .categoryId(1)
                .value(BigDecimal.ONE)
                .build();

        AccountEntity accountFound = AccountEntity.builder().id(1).build();

        when(accounts.findById(any())).thenReturn(Optional.of(accountFound));
        var exception = assertThrows(NotFoundException.class, () -> createTransactions.process(transaction));
        assertEquals("Category not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass transaction ENTRADA Should increase value at the Account")
    public void whenPassTransactionENTRADAShouldIncreaseValueAccount(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .categoryId(1)
                .value(BigDecimal.TEN)
                .build();

        AccountEntity accountFound = AccountEntity.builder()
                .id(1)
                .balance(BigDecimal.TEN)
                .income(BigDecimal.TEN)
                .expense(BigDecimal.TEN)
                .build();
        CategoryEntity categoryFound = CategoryEntity.builder()
                .id(1)
                .type(CategoryType.ENTRADA).build();

        when(accounts.findById(any())).thenReturn(Optional.of(accountFound));
        when(categories.findById(any())).thenReturn(Optional.of(categoryFound));

        Transaction transactionNew = createTransactions.process(transaction);
        then(transactions).should(times(1)).save(any());
        then(accounts).should(times(1)).save(any());
        assertNotNull(transactionNew);
        assertEquals(20,accountFound.getBalance().intValue());
        assertEquals(20,accountFound.getIncome().intValue());
        assertEquals(10,accountFound.getExpense().intValue());

    }

    @Test
    @DisplayName("Test when Pass transaction SAIDA Should decrease value at the Account")
    public void whenPassTransactionSAIDAShouldDecreaseValueAccount(){
        Transaction transaction = Transaction.builder()
                .accountId(1)
                .categoryId(1)
                .value(BigDecimal.TEN)
                .build();

        AccountEntity accountFound = AccountEntity.builder()
                .id(1)
                .balance(BigDecimal.TEN)
                .income(BigDecimal.TEN)
                .expense(BigDecimal.TEN)
                .build();
        CategoryEntity categoryFound = CategoryEntity.builder()
                .id(1)
                .type(CategoryType.SAIDA).build();

        when(accounts.findById(any())).thenReturn(Optional.of(accountFound));
        when(categories.findById(any())).thenReturn(Optional.of(categoryFound));

        Transaction transactionNew = createTransactions.process(transaction);
        then(transactions).should(times(1)).save(any());
        then(accounts).should(times(1)).save(any());
        assertNotNull(transactionNew);
        assertEquals(0,accountFound.getBalance().intValue());
        assertEquals(10,accountFound.getIncome().intValue());
        assertEquals(20,accountFound.getExpense().intValue());

    }


}