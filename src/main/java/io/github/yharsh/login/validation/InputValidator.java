package io.github.yharsh.login.validation;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    @Autowired
    @Qualifier("passwordValidator")
    private Validator passwordValidator;
    @Autowired
    @Qualifier("emailIdValidator")
    private Validator emailIdValidator;
    @Autowired
    @Qualifier("captchaValidator")
    private CaptchaValidator captchaValidator;

    public void validate(User user) throws Exception {
        if (user == null) {
            throw new BadRequestException("Invalid input");
        } else if (!emailIdValidator.validate(user.getEmailId())) {
            throw new BadRequestException("Invalid Email id");
        } else if (!passwordValidator.validate(user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        } else if (!captchaValidator.validate(user.getRecaptchaToken())) {
            throw new BadRequestException("Invalid captcha");
        }
    }
}
