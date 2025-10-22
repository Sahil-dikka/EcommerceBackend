package com.shopify.ecommerce.service;


import com.shopify.ecommerce.exception.UserException;
import com.shopify.ecommerce.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
