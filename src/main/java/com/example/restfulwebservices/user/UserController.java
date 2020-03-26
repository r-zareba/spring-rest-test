package com.example.restfulwebservices.user;

import com.example.restfulwebservices.company.CompanyController;
import com.example.restfulwebservices.exception.NotFoundException;
import com.example.restfulwebservices.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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
    private UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}")
    public User getById(@PathVariable int id) {
        User user = service.findById(id);

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

    @GetMapping(path = "/{id}/tasks")
    public List<Task> getTasksForUser(@PathVariable int id) {
        User user = service.findById(id);
        return user.getTasks();
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // Add Location header in HTTP response
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}