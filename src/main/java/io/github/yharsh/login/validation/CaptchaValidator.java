package io.github.yharsh.login.validation;

import io.github.yharsh.login.captcha.CaptchaService;
import io.github.yharsh.login.exception.ReCaptchaInvalidException;
import io.github.yharsh.login.exception.ReCaptchaUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "captchaValidator")
public class CaptchaValidator implements Validator {
    @Autowired
    private CaptchaService captchaService;

    @Override
    public boolean validate(String captchaToken) {
        boolean valid = true;
        try {
            captchaService.verify(captchaToken);
        } catch (ReCaptchaInvalidException e) {
            valid = false;
        } catch (ReCaptchaUnavailableException e) {
            throw new RuntimeException(e);
        }

        return valid;
    }
}
