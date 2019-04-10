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

    public void validate(User user) throws BadRequestException {
        if (user == null) {
            throw new BadRequestException("Invalid input");
        } else if (!emailIdValidator.validate(user.getEmailId())) {
            throw new BadRequestException("Invalid EmailId");
        } else if (!passwordValidator.validate(user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }
    }
}
