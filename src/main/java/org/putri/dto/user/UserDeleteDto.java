package org.putri.dto.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;

@Data
public class UserDeleteDto {
    @Schema(description = "User ID", example = "1")
    public Long userId;
}
