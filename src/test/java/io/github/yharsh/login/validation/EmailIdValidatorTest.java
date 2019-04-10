package io.github.yharsh.login.validation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailIdValidatorTest {
    @Autowired
    @Qualifier("emailIdValidator")
    private Validator emailIdValidator;

    @Test
    public void testForValidEmailId() {
        Assert.assertTrue(emailIdValidator.validate("abc@domain.com"));
    }

    @Test
    public void testForNullEmailId() {
        Assert.assertFalse(emailIdValidator.validate(null));
    }

    @Test
    public void testForInvalidEmailId() {
        Assert.assertFalse(emailIdValidator.validate("invalidemailid"));
    }

    @Test
    public void testForCaseInsensitivity() {
        Assert.assertTrue(emailIdValidator.validate("USER@dOMAIN.COM"));
    }
}
