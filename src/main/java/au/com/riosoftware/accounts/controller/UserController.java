package au.com.riosoftware.accounts.controller;

import au.com.riosoftware.accounts.model.User;
import au.com.riosoftware.accounts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public Mono<User> findById(final @PathVariable String id) {
        return this.userService.findById(id);
    }

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

}
