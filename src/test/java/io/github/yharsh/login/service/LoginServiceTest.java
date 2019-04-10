package io.github.yharsh.login.service;

import io.github.yharsh.login.LogonApplication;
import io.github.yharsh.login.domain.User;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = LogonApplication.class)
@AutoConfigureMockMvc
public class LoginServiceTest {
    @Autowired
    private MockMvc loginSvc;

    @Test
    public void createUser_should_return_Ok_for_valid_request() throws Exception {
        String emailId = "user@domain.com";
        String password = "Password@123";
        String userJson = "{\"emailId\":\"" + emailId + "\",\"password\":\"" + password + "\"}";
        User user = new User(emailId, password);
        loginSvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
        loginSvc.perform(get("/user?emailId=" + emailId)).andExpect(status().isOk()).andExpect(jsonPath("emailId", Matchers.is(emailId)));
    }

    @Test
    public void createUser_should_return_Bad_Request_for_invalid_request() throws Exception {
        String userJson = "{\"emailId\":\"\",\"password\":\"\"}";
        loginSvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void createUser_should_return_Conflict_for_invalid_request() throws Exception {
        //First create a user
        String emailId = "xyz@domain.com";
        String password = "Password@123";
        String userJson = "{\"emailId\":\"" + emailId + "\",\"password\":\"" + password + "\"}";
        User user = new User(emailId, password);
        loginSvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
        //Now try to create another user identified by emailId
        loginSvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void testGetUser_should_return_Not_Found_when_user_does_not_exist() throws Exception {
        loginSvc.perform(get("/user?emailId=notexisting")).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}
