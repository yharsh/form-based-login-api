package io.github.yharsh.login.services;

import io.github.yharsh.login.domain.User;
import io.github.yharsh.login.exception.BadRequestException;
import io.github.yharsh.login.exception.UserAlreadyExistsException;
import io.github.yharsh.login.exception.UserNotFoundException;
import io.github.yharsh.login.helper.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginService {
    @Autowired
    private UserHelper userHelper;

    @RequestMapping("/user")
    public User getUserDetails(@RequestParam(value = "emailId", defaultValue = "noname@domain.com") String name) {
        try {
            return userHelper.get(name);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void createUser(@RequestBody User user) throws Exception {
        try {
            userHelper.create(user);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
