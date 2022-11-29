package com.example.order.services;

import com.example.order.domain.User;
import com.example.order.domain.exceptions.UnauthorizedException;
import com.example.order.domain.exceptions.UnknownUserException;
import com.example.order.domain.exceptions.WrongPassWordException;
import com.example.order.domain.repos.UserRepo;
import com.example.order.domain.security.EmailPassWord;
import com.example.order.domain.security.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {

    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final UserRepo repository;

    public SecurityService(UserRepo repository) {
        this.repository = repository;
    }

    public void validateAuthorisation(String authorization, Feature feature) throws RuntimeException {
        EmailPassWord usernamePassword = getUseramePassword(authorization);
        User user = repository.getUserById(usernamePassword.getUsername()).orElseThrow(UnknownUserException::new);

        if (!user.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.info("Wrong password");
            throw new WrongPassWordException();
        }
        if (!user.hasAccessTo(feature)) {
            logger.info("This user doesn't have the correct role or access to features");
            throw new UnauthorizedException();
        }

    }

    private EmailPassWord getUseramePassword(String authorization) throws UnauthorizedException {
        try {
            String decodedToUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
            String username = decodedToUsernameAndPassword.split(":")[0];
            String password = decodedToUsernameAndPassword.split(":")[1];
            return new EmailPassWord(username, password);
        }catch (RuntimeException ex){
            throw new UnauthorizedException();
        }
    }
}
