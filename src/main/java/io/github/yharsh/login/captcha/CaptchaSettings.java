package io.github.yharsh.login.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {
    private String siteKey;
    private String secretKey;
    private String siteVerify;

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSiteVerify() {
        return siteVerify;
    }

    public void setSiteVerify(String siteVerify) {
        this.siteVerify = siteVerify;
    }
}