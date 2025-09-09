package org.putri.dto.user;

import java.sql.Date;

import lombok.Data;

@Data
public class UserListDto {
    public Long userId;
    public String username;
    public String email;
    public String password;

    public Date createdAt;
    public Date updatedAt;
    public Date deletedAt;
}
