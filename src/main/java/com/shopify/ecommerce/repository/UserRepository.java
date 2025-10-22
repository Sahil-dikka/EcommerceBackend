package com.shopify.ecommerce.repository;

import com.shopify.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByEmail(String email);

}
