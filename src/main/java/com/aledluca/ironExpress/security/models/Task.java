package com.aledluca.ironExpress.security.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    private Date dueDate;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "users_id")
    User user;
}
