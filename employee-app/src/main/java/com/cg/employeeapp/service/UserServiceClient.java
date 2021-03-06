package com.cg.employeeapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

//import javax.inject.Inject;

public class UserServiceClient {
    private static final String PATH = "api/v1.0/users";
    private final RestTemplate restTemplate;

    // Using Spring's PropertySourcesPlaceholderConfigurer bean, get the content of the USER_ROUTE environment variable
    @Value("${USER_ROUTE}")
    private String userServiceRoute;
    private Logger logger;

//    @Inject
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public boolean isPremiumUser(String id) throws RuntimeException {
        String url = userServiceRoute + "/" + PATH + "/" + id;
        logger.info("sending request {}", url);

        try {
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
            logger.info("received response, status code: {}", responseEntity.getStatusCode());
            return responseEntity.getBody().premiumUser;
        } catch(HttpStatusCodeException error) {
            logger.error("received HTTP status code: {}", error.getStatusCode());
            throw error;
        }
    }

    public static class User {
        // public, so that Jackson can access the field
        public boolean premiumUser;
    }
}
