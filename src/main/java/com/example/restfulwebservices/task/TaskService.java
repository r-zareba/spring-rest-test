package com.example.restfulwebservices.task;

import com.example.restfulwebservices.exception.NotFoundException;
import com.example.restfulwebservices.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByUserId(int id) {
        Optional<List<Task>> tasks = taskRepository.findAllByUserId(id);
        return tasks.orElseThrow(() -> new NotFoundException(String.format("Tasks for user id: %s not found", id)));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
