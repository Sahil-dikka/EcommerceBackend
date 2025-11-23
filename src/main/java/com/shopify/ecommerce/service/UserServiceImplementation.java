package com.shopify.ecommerce.service;

import com.shopify.ecommerce.config.JwtProvider;
import com.shopify.ecommerce.exception.UserException;
import com.shopify.ecommerce.model.User;
import com.shopify.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }

        throw new UserException("user not found with id : " + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findUserByEmail(email);

        if(user!=null){
            return user;
        }

        throw new UserException("User does not Exists with email id ");
    }
}
