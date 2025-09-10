package org.putri.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import org.putri.entity.Task;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    @Inject
    EntityManager em;

    public List<Task> getTasksByUserId(Long userId) {
        return em.createQuery("SELECT t FROM Task t WHERE userId = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
