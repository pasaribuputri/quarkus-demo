package org.putri.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.putri.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
