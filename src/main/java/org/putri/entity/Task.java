package org.putri.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {

    public String description;
    public String title;
    public Integer status;
    public Date dueDate;
    public Integer userId;

    public Date created_at;
    public Date updated_at;
    public Date deleted_at;
}
