package io.github.yharsh.login.helper;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.domain.UserRepository;
import io.github.yharsh.login.exception.ReCaptchaInvalidException;
import io.github.yharsh.login.exception.ReCaptchaUnavailableException;
import io.github.yharsh.login.exception.UserAlreadyExistsException;
import io.github.yharsh.login.exception.UserNotFoundException;
import io.github.yharsh.login.validation.InputValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserHelperTest {
    @MockBean
    private InputValidator inputValidator;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @TestConfiguration
    static class UserHelperTestConfig {
        @Bean
        public UserHelper userHelper() {
            return new UserHelper();
        }
    }

    @Autowired
    private UserHelper userHelper;

    @Test
    public void createUserWithValidInput() throws Exception {
        String emailId = "abc@domain.com";
        String name = "UserXYZ";
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword("Password@123");
        user.setName(name);
        Mockito.doNothing().when(inputValidator).validate(user);
        Mockito.when(userRepository.insert(user)).thenReturn(user);

        userHelper.create(user);

        Mockito.verify(userRepository, Mockito.times(1)).insert(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createShouldThrowExceptionIfUserAlreadyExist() throws Exception {
        User user = new User();
        Mockito.doNothing().when(inputValidator).validate(user);
        Mockito.doThrow(new DuplicateKeyException("Unique key voilated")).when(userRepository).insert(user);

        userHelper.create(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void getShouldThrowUserNotFoundExceptionWhenUserDoesNotExists() throws UserNotFoundException, ReCaptchaUnavailableException, ReCaptchaInvalidException {
        String emailId = "nonexistent@domain.com";
        Mockito.doReturn(null).when(userRepository).findFirstByEmailId(emailId);

        userHelper.get(emailId);
    }

    @Test
    public void getShouldReturnUserWhenUserExists() throws UserNotFoundException, ReCaptchaUnavailableException, ReCaptchaInvalidException {
        String emailId = "nonexistent@domain.com";
        User user = new User();
        user.setEmailId(emailId);
        Mockito.doReturn(user).when(userRepository).findFirstByEmailId(emailId);

        User fetchedUser = userHelper.get(emailId);

        Assert.assertEquals(user, fetchedUser);
    }
}
