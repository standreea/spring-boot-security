package com.springsecurity.springbootsecurity;

import com.springsecurity.springbootsecurity.controller.ManagementController;

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
@WebMvcTest(ManagementController.class)
public class ManagementControllerTest {

    @Autowired
	private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    // @BeforeEach
    // void setUp() {
    //     this.mockMvc = MockMvcBuilders.standaloneSetup(new ManagementController()).build();
    // }
    @BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
    
    @WithMockUser(value = "manager", password = "manager111",roles={"ADMIN","MANAGER"})
    @Test
    public void pathToManagementResoursce_shouldSucceed() throws Exception {
        mockMvc.perform(get("/management/index").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
}
