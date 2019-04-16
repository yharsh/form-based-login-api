package io.github.yharsh.login.captcha;

import io.github.yharsh.login.exception.ReCaptchaInvalidException;
import io.github.yharsh.login.exception.ReCaptchaUnavailableException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@RunWith(SpringRunner.class)
public class CaptchaServiceTest {
    @MockBean
    RestOperations restTemplate;

    @TestConfiguration
    static class CaptchaServiceTestConfiguration {
        @Bean
        public CaptchaService captchaService() {
            return new CaptchaService();
        }

        @Bean
        public CaptchaSettings captchaSettings() {
            return new CaptchaSettings("sitekey", "secretkey", "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s");
        }
    }

    @Autowired
    private CaptchaService captchaService;

    @Test
    public void verifyShouldNotThrowExceptionWhenValidCaptchaTokenIsPassed() throws ReCaptchaUnavailableException, ReCaptchaInvalidException {
        GoogleResponse successResponse = new GoogleResponse();
        successResponse.setSuccess(true);
        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn(successResponse);
        captchaService.verify("token");
    }

    @Test(expected = ReCaptchaInvalidException.class)
    public void verifyShouldThrowExceptionWhenInvalidCaptchaTokenIsPassed() throws ReCaptchaUnavailableException, ReCaptchaInvalidException {
        GoogleResponse successResponse = new GoogleResponse();
        successResponse.setSuccess(false);
        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn(successResponse);
        captchaService.verify("token");
    }

    @Test(expected = ReCaptchaUnavailableException.class)
    public void verifyShouldThrowExceptionWhenUnableToCallRecaptchaApi() throws ReCaptchaUnavailableException, ReCaptchaInvalidException {
        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenThrow(new RestClientException("Unable to open connection"));
        captchaService.verify("token");
    }
}
