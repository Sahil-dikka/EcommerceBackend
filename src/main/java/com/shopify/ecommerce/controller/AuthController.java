package com.shopify.ecommerce.controller;

import com.shopify.ecommerce.config.JwtProvider;
import com.shopify.ecommerce.exception.UserException;
import com.shopify.ecommerce.model.User;
import com.shopify.ecommerce.repository.UserRepository;
import com.shopify.ecommerce.request.LoginRequest;
import com.shopify.ecommerce.response.AuthResponse;
import com.shopify.ecommerce.service.CustomUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImplementation customUserServiceImplementation;

    public AuthController(UserRepository userRepository ,
                          CustomUserServiceImplementation customUserServiceImplementation,
                          PasswordEncoder passwordEncoder,JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.customUserServiceImplementation=customUserServiceImplementation;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;

    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findUserByEmail(email);

        if(isEmailExist!=null){

            throw new UserException("Email Already used with another account");
        }


        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(),newUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Successfull");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){

        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login Successfull");

        //AuthResponse authResponse = new AuthResponse(token,"Login Successfull");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String userName,String password){

        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userName);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid UserName");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
