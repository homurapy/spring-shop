package com.example.springshop.config.security;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.model.User;
import com.example.springshop.repository.UserRepository;
import com.vaadin.flow.router.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private CustomUserDetailsService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        var uuid = UUID.randomUUID();

        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setRole("ADMIN");
        userFromDb.setLogin("123");

        Mockito.doReturn(Optional.of(userFromDb))
                .when(userRepository)
                .findById(1L);

        Optional<User> user = userRepository.findById(1L);
        Assertions.assertNotNull(user.get());
        System.out.println(user.get().getLogin());
    }

    @Test
    public void checkThrow() {

        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setRole("ADMIN");
        userFromDb.setLogin("123");

        Mockito.doReturn(Optional.of(userFromDb))
                .when(userRepository)
                .findById(2L);

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(2L));
    }
}
