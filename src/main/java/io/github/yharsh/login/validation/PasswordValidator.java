package io.github.yharsh.login.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component(value = "passwordValidator")
public class PasswordValidator implements Validator {
    private Pattern pattern;

    public PasswordValidator(@Value("${passwordPattern}") String passwordPattern) {
        pattern = Pattern.compile(passwordPattern);
    }

    @Override
    public boolean validate(String password) {
        return password != null && pattern.matcher(password).matches();
    }
}
