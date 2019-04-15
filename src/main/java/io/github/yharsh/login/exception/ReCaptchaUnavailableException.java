package io.github.yharsh.login.exception;

public class ReCaptchaUnavailableException extends Exception {
    public ReCaptchaUnavailableException(String message, Throwable e) {
        super(message, e);
    }
}
