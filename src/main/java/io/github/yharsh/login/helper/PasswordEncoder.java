package io.github.yharsh.login.helper;

import org.springframework.stereotype.Component;

@Component
class PasswordEncoder {
    String encode(String password) {
        //ToDo use a real encoder
        return password;
    }
}
