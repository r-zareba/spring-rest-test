package com.example.restfulwebservices.user;

import com.example.restfulwebservices.company.CompanyController;
import com.example.restfulwebservices.exception.NotFoundException;
import com.example.restfulwebservices.task.Task;
import com.example.restfulwebservices.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public User getById(@PathVariable int id) {
        User user = userService.findById(id);

        // Company hard coded - TODO
        Link companyLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(CompanyController.class)
                .getById(1)).withRel("company");
        user.add(companyLink);

        Link tasksLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(this.getClass())
                .getTasksForUser(id)).withRel("tasks");

        user.add(tasksLink);
        return user;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }

    @GetMapping(path = "/{id}/tasks", produces = "application/json")
    public List<Task> getTasksForUser(@PathVariable int id) {
        return taskService.getTasksByUserId(id);
    }

    @PostMapping(path = "{id}/tasks", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTaskForUser(@PathVariable int id, @Valid @RequestBody Task task) {
        User user = userService.findById(id);
        task.setUser(user);
        return taskService.save(task);
    }

}