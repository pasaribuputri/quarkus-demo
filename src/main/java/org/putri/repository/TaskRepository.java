package org.putri.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.putri.entity.Task;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
}
