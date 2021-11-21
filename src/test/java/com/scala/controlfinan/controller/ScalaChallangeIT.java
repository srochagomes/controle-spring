package com.scala.controlfinan.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scala.controlfinan.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class ScalaChallangeIT {

    private final static String URI_ACCOUNTS = "/accounts/v1";
    private final static String URI_CATEGORIES = "/categories/v1";
    private final static String URI_TRANSACTIONS = "/transactions/v1";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    @Test
    @DisplayName("Test integration create Account without fields requireds")
    public void testCreateAccountsWithoutFieldsRequireds() throws Exception {

        Account accountNew = Account.builder().build();

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountNew)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test integration create Account with fields requireds and User not found")
    public void testCreateAccountsWithFieldsRequiredsAndUserNotFound() throws Exception {

        Account accountNew = Account.builder().userId(100000)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountNew)))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test integration create Account with fields requireds")
    public void testCreateAccountsWithFieldsRequireds() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountNew)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }


    @Test
    @DisplayName("Test integration create Account without fields requireds")
    public void testCreateCategoryWithoutFieldsRequireds() throws Exception {

        Category categoryNew = Category.builder().build();

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryNew)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("Test integration create Category with fields requireds")
    public void testCreateCategorysWithFieldsRequireds() throws Exception {

        Category categoryNew = Category.builder()
                .name("teste")
                .type(CategoryType.ENTRADA)
                .build();

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryNew)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }


    @Test
    @DisplayName("Test integration Delete Account by id not found")
    public void testDeleteByIdNotFound() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URI_ACCOUNTS.concat("/{id}"), -1))
                .andExpect(status().is4xxClientError());

    }
    @Test
    @DisplayName("Test integration Delete Account by id")
    public void testDeleteById() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();

        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountNew)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

        Account account = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Account.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URI_ACCOUNTS.concat("/{id}"), account.getId()))
                .andDo(print()).andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Test integration Search Account by id")
    public void testSearchAccountsById() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();

        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountNew)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

        Account account = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Account.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ACCOUNTS.concat("/{id}"), -1))
                .andDo(print()).andExpect(status().isNotFound());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ACCOUNTS.concat("/{id}"), account.getId()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URI_ACCOUNTS.concat("/{id}"), account.getId()))
                .andDo(print()).andExpect(status().isNoContent());

    }


    @Test
    @DisplayName("Test integration Search All Account")
    public void testSearchAccountsAll() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();
        final String dataNew = mapper.writeValueAsString(accountNew);

        List<Integer> idsCreated = IntStream.rangeClosed(1, 15)
                .mapToObj(i -> this.callCreateAccount(dataNew)).map(Account::getId).collect(Collectors.toList());


        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ACCOUNTS))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        idsCreated.forEach(id->
                this.callDeleteAccount(id));

        PaginatedResponse<Account> accounts = mapper.readValue(contentAsString,
                new TypeReference<PaginatedResponse<Account>>() { });
        assertNotNull(accounts);
        assertEquals(10,accounts.get().count());
    }

    @Test
    @DisplayName("Test integration Search All Account by user id")
    public void testSearchAccountsAllByUserId() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();
        final String dataNew = mapper.writeValueAsString(accountNew);

        List<Integer> idsCreated = IntStream.rangeClosed(1, 15)
                .mapToObj(i -> this.callCreateAccount(dataNew)).map(Account::getId).collect(Collectors.toList());


        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ACCOUNTS)
                        .queryParam("userId",String.valueOf(accountNew.getUserId()))
                )
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        idsCreated.forEach(id->
                this.callDeleteAccount(id));

        PaginatedResponse<Account> accounts = mapper.readValue(contentAsString,
                new TypeReference<PaginatedResponse<Account>>() { });
        assertNotNull(accounts);
        assertEquals(10,accounts.get().count());
    }

    private void callDeleteAccount(Integer id){
        try {
            this.mockMvc
                    .perform(MockMvcRequestBuilders.delete(URI_ACCOUNTS.concat("/{id}"), id))
                    .andDo(print()).andExpect(status().isNoContent());
        }catch(Exception e){
            Assert.assertTrue(false);  ;
        }
    }

    @Test
    @DisplayName("Test integration Search Category")
    public void testSearchCategory() throws Exception {

        Category categoryNew = Category.builder()
                .name("Teste")
                .type(CategoryType.ENTRADA)
                .build();

        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryNew)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

        Category category = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Category.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_CATEGORIES.concat("/{id}"), -1))
                .andDo(print()).andExpect(status().isNotFound());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_CATEGORIES.concat("/{id}"), category.getId()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_CATEGORIES))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_CATEGORIES)
                        .queryParam("name",String.valueOf(category.getName()))
                )
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }


    @Test
    @DisplayName("Test integration create Transaction with fields requireds")
    public void testCreateTransactionWithFieldsRequireds() throws Exception {

        Account accountNew = Account.builder().userId(1)
                .income(BigDecimal.ONE)
                .expense(BigDecimal.ONE)
                .balance(BigDecimal.ONE)
                .build();
        final String accountNewText = mapper.writeValueAsString(accountNew);

        Account account = this.callCreateAccount(accountNewText);

        Category categoryNew = Category.builder()
                .name("teste")
                .type(CategoryType.ENTRADA)
                .build();

        final String categoryNewText = mapper.writeValueAsString(categoryNew);

        Category category = this.callCreateCategory(categoryNewText);

        Transaction transactionNew = Transaction.builder()
                .accountId(account.getId())
                .categoryId(category.getId())
                .value(BigDecimal.TEN)
                .build();
        final String transactionNewText = mapper.writeValueAsString(transactionNew);
        Transaction transaction = this.callCreateTransaction(transactionNewText);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_TRANSACTIONS.concat("/accounts/{id}"), account.getId()))
                .andDo(print()).andExpect(status().isOk());


    }

    private Account callCreateAccount(String dataNew){
        String contentAsString = null;
        MvcResult mvcResult  = null;
        try {
            mvcResult = this.mockMvc
                    .perform(MockMvcRequestBuilders.post(URI_ACCOUNTS)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dataNew))
                    .andExpect(status().isCreated())
                    .andReturn();
            contentAsString =  mvcResult.getResponse().getContentAsString();

            return mapper.readValue(contentAsString,Account.class);
        }catch(Exception e){
            return null;
        }
    }

    private Category callCreateCategory(String categoryNew){
        String contentAsString = null;
        MvcResult mvcResult  = null;
        try {
            mvcResult = this.mockMvc
                    .perform(MockMvcRequestBuilders.post(URI_CATEGORIES)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(categoryNew))
                    .andDo(print()).andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                    .andReturn();

            contentAsString =  mvcResult.getResponse().getContentAsString();

            return mapper.readValue(contentAsString,Category.class);
        }catch(Exception e){
            return null;
        }
    }

    private Transaction callCreateTransaction(String transactionNew){

        String contentAsString = null;
        MvcResult mvcResult  = null;
        try {
            mvcResult = this.mockMvc
                    .perform(MockMvcRequestBuilders.post(URI_TRANSACTIONS)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(transactionNew))
                    .andDo(print()).andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                    .andReturn();

            contentAsString =  mvcResult.getResponse().getContentAsString();

            return mapper.readValue(contentAsString,Transaction.class);
        }catch(Exception e){
            return null;
        }
    }

}