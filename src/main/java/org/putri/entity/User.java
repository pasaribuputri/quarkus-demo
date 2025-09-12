package org.putri.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    public String username;
    public String email;
    public String password;
    public String fullname;

    @Column(name = "profile_path")
    public String profilePath;
    @Column(name = "job_title")
    public String JobTitle;
    @Column(name = "phone_number")
    public String phoneNumber;
    public String address;
    @Column(name = "birth_date")
    public Date birthDate;

    public Date createdAt;
    public Date updatedAt;
    public Date deletedAt;
}
