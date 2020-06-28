package com.webshop.user;

import com.webshop.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByMail(String mail) { return userRepository.findUserByEmail(mail);}

    public void isUserVerified(String email) {
        User user = userRepository.findUserByEmail(email);
        if(!user.isVerified() && user.isTwoFactorAuth()) {
            throw new NotAuthorizedException("Your account is not verified!\n Please add account to your application.");
        }
    }
}
