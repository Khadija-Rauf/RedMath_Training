package com.redmath.training;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNewsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news?title=%e%"))
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    public void testNewsGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/1"))
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    public void testNewsPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .with(SecurityMockMvcRequestPostProcessors.user("REPORTER").authorities(new SimpleGrantedAuthority("REPORTER")) )
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"id\":6,\"title\":\"title 6\",\"details\":\"details 6\",\"tags\":\"tags 6\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}