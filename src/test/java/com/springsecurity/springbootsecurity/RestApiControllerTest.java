package com.springsecurity.springbootsecurity;

import com.springsecurity.springbootsecurity.controller.RestApiController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestApiController.class)
public class RestApiControllerTest {

    @Autowired
	private WebApplicationContext context;
    
    private MockMvc mockMvc;

    @BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}

    @WithMockUser(value = "johndoe", password = "johndoe111",authorities={"ACCESS_TEST1"})
    @Test
    public void shouldReturnTest1Message() throws Exception {
        mockMvc.perform(get("/api/public/test1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("API Test 1")));
    }
    
    @WithMockUser(value = "johndoe", password = "johndoe111",authorities={"ACCESS_TEST2"})
    @Test
    public void shouldReturnTest2Message() throws Exception {
        mockMvc.perform(get("/api/public/test2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("API Test 2")));
    }

}
