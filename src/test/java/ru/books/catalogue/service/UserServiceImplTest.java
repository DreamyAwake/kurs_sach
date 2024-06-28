package ru.books.catalogue.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.books.catalogue.exceptions.UsernameAlreadyExistsException;
import ru.books.catalogue.model.User;
import ru.books.catalogue.model.UserRole;
import ru.books.catalogue.repository.UserRepository;
import ru.books.catalogue.repository.UserRoleRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegistration_WhenUsernameDoesNotExist_ThenUserIsRegistered() {
        String username = "testuser";
        String password = "testpassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        userService.registration(username, password);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void testRegistration_WhenUsernameExists_ThenUsernameAlreadyExistsExceptionThrown() {

        String username = "existinguser";
        String password = "testpassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registration(username, password));
        verify(userRepository, never()).save(any(User.class));
        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void testGetUserById_WhenUserExists_ThenReturnUser() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(userId);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testGetUserById_WhenUserDoesNotExist_ThenReturnEmptyOptional() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserById(userId);
        assertTrue(result.isEmpty());
    }

    @Test
    void testLoadUserByUsername_WhenUserExists_ThenReturnUserDetails() {
        String username = "testuser";
        User user = new User().setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_WhenUserDoesNotExist_ThenThrowUsernameNotFoundException() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}