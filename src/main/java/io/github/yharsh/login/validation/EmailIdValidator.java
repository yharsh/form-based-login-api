package io.github.yharsh.login.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component(value = "emailIdValidator")
public class EmailIdValidator implements Validator {
    private Pattern pattern;

    public EmailIdValidator(@Value("${emailIdPattern}") String emailIdPattern) {
        pattern = Pattern.compile(emailIdPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean validate(String emailId) {
        return emailId != null && pattern.matcher(emailId).matches();
    }
}
