package io.github.yharsh.login.helper;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.domain.UserRepository;
import io.github.yharsh.login.exception.UserAlreadyExistsException;
import io.github.yharsh.login.exception.UserNotFoundException;
import io.github.yharsh.login.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    private static Logger logger = LoggerFactory.getLogger(UserHelper.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InputValidator inputValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(User user) throws Exception {
        inputValidator.validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.insert(user);
        } catch (DuplicateKeyException e) {
            logger.error("User creation failed", e);
            throw new UserAlreadyExistsException("Email id is already taken");
        }
    }

    public User get(String emailId) throws UserNotFoundException {
        User user = userRepository.findFirstByEmailId(emailId);
        if (user == null) {
            throw new UserNotFoundException("No user found with emailId: " + emailId);
        }
        return user;
    }
}
