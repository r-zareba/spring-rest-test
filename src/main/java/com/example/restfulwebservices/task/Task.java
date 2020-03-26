package com.example.restfulwebservices.task;

import com.example.restfulwebservices.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties({"user"})
public class Task {

    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Past
    private Date createdDate;
}
