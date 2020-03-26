package com.example.restfulwebservices.user;


import com.example.restfulwebservices.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@JsonIgnoreProperties({"tasks"})
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Past
    private Date createdDate;
    private String companyName;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

}
