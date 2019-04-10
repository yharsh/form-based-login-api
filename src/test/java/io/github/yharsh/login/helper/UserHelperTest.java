package io.github.yharsh.login.helper;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.exception.UserAlreadyExistsException;
import io.github.yharsh.login.exception.UserNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserHelperTest {
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
        userHelper.create(user);

        User createdUser = userHelper.get(emailId);
        Assert.assertEquals(emailId, createdUser.getEmailId());
        Assert.assertEquals(name, createdUser.getName());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createShouldThrowExceptionIfUserAlreadyExist() throws Exception {
        String emailId = "existing@domain.com";
        String password = "Password@123";
        userHelper.create(new User(emailId, password));
        //Try to create same user
        userHelper.create(new User(emailId, password));
    }

    @Test(expected = UserNotFoundException.class)
    public void testForNonExistingUser() throws UserNotFoundException {
        userHelper.get("nonexisting@email.id");
    }
}
