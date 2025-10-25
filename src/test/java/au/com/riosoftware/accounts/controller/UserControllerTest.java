package au.com.riosoftware.accounts.controller;

import au.com.riosoftware.accounts.model.User;
import au.com.riosoftware.accounts.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);

        testUser1 = new User(
            "test1@example.com",
            "testuser1",
            "hashedpassword1",
            "Test",
            "User1"
        );
        testUser1.setId(UUID.randomUUID().toString());
        testUser1.setCreatedAt(LocalDateTime.now());
        testUser1.setUpdatedAt(LocalDateTime.now());

        testUser2 = new User(
            "test2@example.com",
            "testuser2",
            "hashedpassword2",
            "Test",
            "User2"
        );
        testUser2.setId(UUID.randomUUID().toString());
        testUser2.setCreatedAt(LocalDateTime.now());
        testUser2.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testFindAllUsers() {

        when(userService.findAll())
            .thenReturn(Flux.just(testUser1, testUser2));

    
        Flux<User> result = userController.findAll();

   
        StepVerifier.create(result)
            .expectNext(testUser1)
            .expectNext(testUser2)
            .verifyComplete();
    }

    @Test
    void testFindAllUsers_whenNoUsers() {
        
        when(userService.findAll())
            .thenReturn(Flux.empty());

     
        Flux<User> result = userController.findAll();

     
        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();
    }
}
