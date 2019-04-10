package io.github.yharsh.login.validation;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InputValidatorTest {
    @Autowired
    private InputValidator inputValidator;

    @Test(expected = BadRequestException.class)
    public void testForBadRequestExceptionWhenNullIsPassed() throws BadRequestException {
        inputValidator.validate(null);
    }

    @Test(expected = BadRequestException.class)
    public void testForBadRequestExceptionWhenEmailIdIsInvalid() throws BadRequestException {
        User user = new User();
        user.setPassword("ValidPassword@15");
        inputValidator.validate(user);
    }

    @Test(expected = BadRequestException.class)
    public void testForBadRequestExceptionWhenPasswordIsInvalid() throws BadRequestException {
        User user = new User();
        user.setEmailId("validId@domain.com");
        inputValidator.validate(user);
    }

    @Test
    public void testForValidInput() throws BadRequestException {
        User user = new User();
        user.setEmailId("validId@domain.com");
        user.setPassword("ValidPassword@15");
        inputValidator.validate(user);
    }
}
