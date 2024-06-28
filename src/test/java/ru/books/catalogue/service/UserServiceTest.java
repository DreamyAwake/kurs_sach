package ru.books.catalogue.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.books.catalogue.model.User;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserService userService;

    @Test
    void testRegistration() {
        String username = "testUser";
        String password = "password";

        userService.registration(username, password);

        verify(userService, times(1)).registration(username, password);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        userService.deleteUserById(userId);

        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User mockUser = new User(1L, "mockUser", "password", true, false, false, new ArrayList<>());
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        assertTrue(userService.getUserById(userId).isPresent());
        assertEquals(mockUser, userService.getUserById(userId).get());
    }
}