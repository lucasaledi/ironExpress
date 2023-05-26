package com.aledluca.ironExpress.security.dto;

import lombok.*;

import java.util.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private String task;
    private Date dueDate;
}
