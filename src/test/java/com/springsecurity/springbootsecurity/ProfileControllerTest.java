package com.springsecurity.springbootsecurity;

import com.springsecurity.springbootsecurity.controller.ProfileController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    private MockMvc mockMvc;

    @Autowired
	private WebApplicationContext context;
   
    @BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
    
    @WithMockUser(value = "johndoe", password = "johndoe111")
    @Test
    public void pathToProfileResoursce_shouldSucceedWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/profile/index").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }   
  
}
