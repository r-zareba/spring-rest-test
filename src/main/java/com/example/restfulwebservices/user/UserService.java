package com.example.restfulwebservices.user;

import com.example.restfulwebservices.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User findById(int id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User id: %s not found", id));
        }
        return user.get();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }


}
