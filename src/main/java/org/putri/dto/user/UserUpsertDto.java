package org.putri.dto.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "User")
public class UserUpsertDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    public Long userId;

    @Schema(description = "Email of the user", example = "test@gmail.com")
    public String email;

    @Schema(description = "Password of the user", example = "password123")
    public String password;

    @Schema(description = "Username of the user", example = "putri")
    public String username;

}
