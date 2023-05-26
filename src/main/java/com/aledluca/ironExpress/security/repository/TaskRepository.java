package com.aledluca.ironExpress.security.repository;

import com.aledluca.ironExpress.security.models.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}