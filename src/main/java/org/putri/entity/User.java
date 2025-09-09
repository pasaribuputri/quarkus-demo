package org.putri.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    public String username;
    public String email;
    public String password;

    public Date createdAt;
    public Date updatedAt;
    public Date deletedAt;
}
