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
public class PasswordValidatorTest {
    @Autowired
    @Qualifier("passwordValidator")
    private Validator passwordValidator;

    @Test
    public void validPassword() {
        Assert.assertTrue(passwordValidator.validate("Shiv@001"));
    }

    @Test
    public void nullPassword() {
        Assert.assertFalse(passwordValidator.validate(null));
    }

    @Test
    public void invalidPasswordMinCharViolation() {
        Assert.assertFalse(passwordValidator.validate("Abc@1")); //Should be of length 6
    }

    @Test
    public void invalidPasswordMinUpperCaseViolation() {
        Assert.assertFalse(passwordValidator.validate("shiv@0001"));
    }

    @Test
    public void invalidPasswordMinLowerCaseViolation() {
        Assert.assertFalse(passwordValidator.validate("SHIV@0001"));
    }

    @Test
    public void invalidPasswordMinSpecialCharacterViolation() {
        Assert.assertFalse(passwordValidator.validate("Shiv001"));
    }
}
