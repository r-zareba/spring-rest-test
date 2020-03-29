package com.example.restfulwebservices.user;

import com.example.restfulwebservices.company.CompanyController;
import com.example.restfulwebservices.exception.NotFoundException;
import com.example.restfulwebservices.task.Task;
import com.example.restfulwebservices.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @GetMapping(path = "/{id}")
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

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        // Add Location header in HTTP response
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/tasks")
    public List<Task> getTasksForUser(@PathVariable int id) {
        return taskService.getTasksByUserId(id);
    }

    @PostMapping(path = "{id}/tasks")
    public Task createTaskForUser(@PathVariable int id, @Valid @RequestBody Task task) {
        User user = userService.findById(id);
        task.setUser(user);
        return taskService.save(task);
    }

}