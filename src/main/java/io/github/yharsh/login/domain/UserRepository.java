package io.github.yharsh.login.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findFirstByEmailId(String emailId);
}
