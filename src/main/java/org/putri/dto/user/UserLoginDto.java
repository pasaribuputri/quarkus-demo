package org.putri.dto.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "User Login")
public class UserLoginDto {
    @Schema(description = "Email of the user", example = "test@gmail.com")
    public String email;

    @Schema(description = "Password of the user", example = "password123")
    public String password;
}
