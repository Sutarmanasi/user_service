package com.cg.employeeapp.config.test;

import com.cg.employeeapp.service.UserServiceClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
public class TestAppContextConfig {
    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        // provide a mock so that the @Value annotation can be resolved even if the environment variable is not set
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.setProperty("USER_ROUTE", ""); // ensures that USER_ROUTE is initialized with no "real" route
        pspc.setProperties(properties);
        return pspc;
    }

    @Bean
    @Primary
        // preferred bean when there are multiple candidates
    UserServiceClient userServiceClient() {
        // return a UserServiceClient mock that just returns "true" for isPremiumUser, without issuing a network request
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        Mockito.when(userServiceClient.isPremiumUser(Mockito.anyString())).thenReturn(true);
        return userServiceClient;
    }
}
