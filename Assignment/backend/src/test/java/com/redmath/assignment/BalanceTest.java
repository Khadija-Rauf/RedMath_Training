package com.redmath.assignment;

import com.redmath.assignment.account.AccountService;
import com.redmath.assignment.balance.Balance;
import com.redmath.assignment.balance.BalanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;
    private AccountService accountService;

    @Test
    public void testFindAll() throws Exception {
        List<Balance> balanceList = generateSampleBalanceList(5); // Change 5 to the desired number of articles

        Mockito.when(balanceService.findAll()).thenReturn(balanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts/balances").with(testUser("Admin", "ADMIN"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(balanceList.size()));

    }
    // Helper method to generate sample news articles
    private List<Balance> generateSampleBalanceList(int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(i -> {
                    Balance balance = new Balance();
                    balance.setId((long) i);
                    balance.setDate(LocalDate.parse("2023-01-01"));
                    balance.setAmount(Double.parseDouble("5000"));
//                    balance.setAccount_id(2L);
                    balance.setIndicator("1");
                    return balance;
                })
                .collect(Collectors.toList());
    }
//    @Test
//    public void testFindById() throws Exception {
//        Long sampleId = 1L;
//        Balance balance = new Balance();
//        balance.setId(sampleId);
//        balance.setDate(LocalDate.parse("2023-01-01"));
//        balance.setAmount(Double.parseDouble("5000"));
//        balance.setAccount(accountService.findById(2L));
//        balance.setIndicator("1");
//
//        Mockito.when(balanceService.getBalance(Mockito.anyLong())).thenReturn(Optional.of(balance));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts/balance/{id}", sampleId).with(testUser("Admin", "ADMIN")))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(balance.getDate()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(balance.getAmount()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.indicator").value(balance.getIndicator()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.account_id").value(balance.getAccount()));
//    }
    @Test
    public void testCreate() throws Exception {
        Long sampleId = 1L;
        Balance balance = new Balance();
        balance.setId(sampleId);
        balance.setDate(LocalDate.parse("2023-01-01"));
        balance.setAmount(5000.0);
        balance.setIndicator("1");
        Mockito.when(balanceService.create(Mockito.any(Balance.class))).thenReturn(balance);
        String jsonRequest = "{\"date\":\"2023-01-01\",\"amount\":5000.0,\"indicator\":\"1\",\"account\":{\"id\":2}}";
        mockMvc.perform(MockMvcRequestBuilders.post("/bank/accounts/balances").with(testUser("Admin", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(balance.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(balance.getDate().toString())) // Convert LocalDate to string
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(balance.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.indicator").value(balance.getIndicator()));
    }

    private RequestPostProcessor testUser(String userName, String authoriy) {
        return SecurityMockMvcRequestPostProcessors.user(userName).authorities(new SimpleGrantedAuthority(authoriy));
    }
}
