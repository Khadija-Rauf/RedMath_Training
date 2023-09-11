package com.redmath.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redmath.assignment.account.AccountService;
import com.redmath.assignment.account.Account;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testFindAll() throws Exception {
        List<Account> accountList = generateSampleAccountList(5); // Change 5 to the desired number of articles

        Mockito.when(accountService.findAll()).thenReturn(accountList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts").with(testUser("Admin", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(accountList.size()));

    }
    // Helper method to generate sample news articles
    private List<Account> generateSampleAccountList(int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(i -> {
                    Account account = new Account();
                    account.setId((long) i);
                    account.setName("Aila");
                    account.setPassword("Aila");
                    account.setEmail("ailanoah81@gmail.com");
                    account.setAddress("Lahore");
                    account.setRole("USER");
                    return account;
                })
                .collect(Collectors.toList());
    }
    @Test
    public void testFindById() throws Exception {
        Long sampleId = 1L;
        Account account = new Account();
        account.setId(sampleId);
        account.setName("Aila");
        account.setPassword("Aila");
        account.setEmail("ailanoah81@gmail.com");
        account.setAddress("Lahore");
        account.setRole("USER");

        Mockito.when(accountService.findById(Mockito.anyLong())).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/accounts/{id}", sampleId).with(testUser("Aila", "USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(account.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(account.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(account.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(account.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(account.getRole()));
    }
    @Test
    public void testUpdate() throws Exception {
        Long sampleId = 1L;
        Account account = new Account();
        account.setId(sampleId);
        account.setName("Aila");
        account.setPassword("Aila");
        account.setEmail("ailanoah81@gmail.com");
        account.setAddress("Lahore");
        account.setRole("USER");

        Mockito.when(accountService.updateAccount(Mockito.eq(sampleId), Mockito.any(Account.class))).thenReturn(account);

        ObjectMapper objectMapper = new ObjectMapper();
        String accountJson = objectMapper.writeValueAsString(account);

        mockMvc.perform(MockMvcRequestBuilders.put("/bank/accounts/{id}", sampleId).with(testUser("Admin", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson)) // Use the JSON representation of the account
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(account.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(account.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(account.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(account.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(account.getRole()));
    }

    @Test
    public void testCreate() throws Exception {
        Long sampleId = 4L;
        Account account = new Account();
        account.setId(sampleId);
            account.setName("Aila");
            account.setPassword("Aila");
            account.setEmail("ailanoah81@gmail.com");
            account.setAddress("Lahore");
            account.setRole("USER");

        Mockito.when(accountService.create(Mockito.any(Account.class))).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.post("/bank/accounts").with(testUser("Admin", "ADMIN")).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"Name\":\"Updated Name\",\"Password\":\"Updated Password\",\"Email\":\"Updated Email\",\"Address\":\"Updated Address\",\"Role\":\"Updated Role\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(account.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(account.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(account.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(account.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(account.getRole()));
    }

    private RequestPostProcessor testUser(String userName, String authoriy) {
        return SecurityMockMvcRequestPostProcessors.user(userName).authorities(new SimpleGrantedAuthority(authoriy));
    }
}
