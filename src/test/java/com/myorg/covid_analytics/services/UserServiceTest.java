package com.myorg.covid_analytics.services;

import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.security.UserRepository;
import com.myorg.covid_analytics.services.security.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService    service;

    @Test
    void findByUsername() {
        String username = "admin";

        User user = User.builder().username("user").mail("mail1@mail.com").build();

        when(repository.findByUsername(username)).thenReturn(user);

        User test = service.findByUsername(username);

        Assertions.assertNotNull(test);
    }

    @Test
    void findByMail() {
        String username = "mail1@mail.com";
        User user = User.builder().username("user").mail("mail1@mail.com").build();

        when(repository.findByMail(username)).thenReturn(user);

        User test = service.findByMail(username);

        Assertions.assertNotNull(test);
    }

    @Test
    void findByUsernameOrMail() {
        String username = "user";
        User user = User.builder().username("user").mail("mail1@mail.com").build();

        when(repository.findByUsernameOrMail(username)).thenReturn(user);

        User test = service.findByUsernameOrMail(username);

        Assertions.assertNotNull(test);
    }

    @Test
    void findAllFilter() {
        User user = User.builder().username("user").mail("mail1@mail.com").build();

        List<User> list = List.of(user);

        when(repository.findAllFilter(anyBoolean(), isNull(), isNull(), isNull(),
                any(Pageable.class))).thenReturn(list);
        List<User> test = service.findAllFilter(true, null, null, null, 1, 0, null);
        Assertions.assertNotNull(test);
    }
}
