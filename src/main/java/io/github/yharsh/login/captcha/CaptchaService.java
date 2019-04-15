package io.github.yharsh.login.captcha;

import io.github.yharsh.login.exception.ReCaptchaInvalidException;
import io.github.yharsh.login.exception.ReCaptchaUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Service
public class CaptchaService {
    @Autowired
    private CaptchaSettings captchaSettings;
    @Autowired
    private RestOperations restTemplate;

    public void verify(final String captchaToken) throws ReCaptchaInvalidException, ReCaptchaUnavailableException {
        final URI verifyUri = URI.create(String.format(captchaSettings.getSiteVerify(), captchaSettings.getSecretKey(), captchaToken));
        try {
            final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
            if (!googleResponse.isSuccess()) {
                throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
            }
        } catch (RestClientException rce) {
            throw new ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce);
        }
    }
}