package io.github.yharsh.login.helper;

import io.github.yharsh.login.captcha.CaptchaService;
import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.exception.ReCaptchaInvalidException;
import io.github.yharsh.login.exception.ReCaptchaUnavailableException;
import io.github.yharsh.login.exception.UserAlreadyExistsException;
import io.github.yharsh.login.exception.UserNotFoundException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserHelperTest {
    @Autowired
    private UserHelper userHelper;
    @MockBean
    private CaptchaService captchaService;

    @Test
    public void createUserWithValidInput() throws Exception {
        String emailId = "abc@domain.com";
        String name = "UserXYZ";
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword("Password@123");
        user.setName(name);
        Mockito.doNothing().when(captchaService).verify(Matchers.anyString());
        userHelper.create(user);

        User createdUser = userHelper.get(emailId);
        Assert.assertEquals(emailId, createdUser.getEmailId());
        Assert.assertEquals(name, createdUser.getName());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createShouldThrowExceptionIfUserAlreadyExist() throws Exception {
        String emailId = "existing@domain.com";
        String password = "Password@123";
        Mockito.doNothing().when(captchaService).verify(Matchers.anyString());
        userHelper.create(new User(emailId, password));
        //Try to create same user
        userHelper.create(new User(emailId, password));
    }

    @Test(expected = UserNotFoundException.class)
    public void testForNonExistingUser() throws UserNotFoundException, ReCaptchaUnavailableException, ReCaptchaInvalidException {
        Mockito.doNothing().when(captchaService).verify(Matchers.anyString());
        userHelper.get("nonexisting@email.id");
    }
}
