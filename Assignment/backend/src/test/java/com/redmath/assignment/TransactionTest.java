package com.redmath.assignment;

import com.redmath.assignment.balance.Balance;
import com.redmath.assignment.balance.BalanceService;
import com.redmath.assignment.transaction.Transaction;
import com.redmath.assignment.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testFindAll() throws Exception {
        List<Transaction> transactionList = generateSampleTransactionList(5); // Change 5 to the desired number of articles

        Mockito.when(transactionService.findAll()).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts/transactions").with(testUser("Admin", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(transactionList.size()));

    }
    // Helper method to generate sample news articles
    private List<Transaction> generateSampleTransactionList(int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(i -> {
                    Transaction transaction = new Transaction();
                    transaction.setId((long) i);
                    transaction.setDate(LocalDate.parse("2023-01-01"));
                    transaction.setDescription("Online Purchase");
                    transaction.setAmount(Double.parseDouble("1000"));
                    transaction.setIndicator("1");
                    return transaction;
                })
                .collect(Collectors.toList());
    }
//    @Test
//    public void testFindById() throws Exception {
//        Long sampleId = 1L;
//        List<Transaction> transactions = new ArrayList<>();
//        Transaction transaction = new Transaction();
//        transaction.setId(sampleId);
//        transaction.setDate(LocalDate.parse("2023-01-01"));
//        transaction.setDescription("Online Purchase");
//        transaction.setAmount(Double.parseDouble("1000"));
//        transaction.setAccount_id(1L);
//        transaction.setIndicator("1");
//        transactions.add(transaction);
//
//        Mockito.when(transactionService.getTransactionsByAccountId(Mockito.anyLong())).thenReturn(transactions);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts/transactions/{id}", sampleId).with(testUser("Admin", "ADMIN")))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(transaction.getDate()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(transaction.getAmount()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(transaction.getDescription()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].indicator").value(transaction.getIndicator()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account_id").value(transaction.getAccount_id()));
//    }
@Test
public void testCreate() throws Exception {
    Long sampleId = 1L;
    Transaction transaction = new Transaction();
    transaction.setId(sampleId);
    transaction.setDate(LocalDate.parse("2023-01-01"));
    transaction.setDescription("Online Purchase");
    transaction.setAmount(Double.parseDouble("1000"));
    transaction.setIndicator("1");

    Mockito.when(transactionService.create(Mockito.any(Long.class), Mockito.any(Transaction.class))).thenReturn(transaction);

    mockMvc.perform(MockMvcRequestBuilders.post("/bank/accounts/transactions/1").with(testUser("Admin", "ADMIN"))
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                    .contentType("application/json")
                    .content("{\"date\":\"2023-01-01\",\"amount\":1000,\"description\":\"Online Purchase\",\"indicator\":\"1\",\"account\":{\"id\":2}}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(transaction.getDate().toString())) // Convert LocalDate to String
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(transaction.getAmount()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(transaction.getDescription()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.indicator").value(transaction.getIndicator()));
}

    private RequestPostProcessor testUser(String userName, String authoriy) {
        return SecurityMockMvcRequestPostProcessors.user(userName).authorities(new SimpleGrantedAuthority(authoriy));
    }

}
