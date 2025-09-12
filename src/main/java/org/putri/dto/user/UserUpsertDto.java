package org.putri.dto.user;


import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;


@Data
@Schema(description = "User")
public class UserUpsertDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    @FormParam("userId")
    @PartType(MediaType.TEXT_PLAIN)
    public Long userId;

    @Schema(description = "Email of the user", example = "test@gmail.com")
    @FormParam("email")
    @PartType(MediaType.TEXT_PLAIN)
    public String email;

    @Schema(description = "Password of the user", example = "password123")
    @FormParam("password")
    @PartType(MediaType.TEXT_PLAIN)
    public String password;

    @Schema(description = "Username of the user", example = "putri")
    @FormParam("username")
    @PartType(MediaType.TEXT_PLAIN)
    public String username;

    @Schema(description = "Fullname of the user", example = "Putri Mulyani")
    @FormParam("fullname")
    @PartType(MediaType.TEXT_PLAIN)
    public String fullname;

    @Schema(description = "Profile path of the user", example = "/images/profile.jpg")
    public String profilePath;

    @Schema(description = "Job title of the user", example = "Software Engineer")
    @FormParam("jobTitle")
    @PartType(MediaType.TEXT_PLAIN)
    public String JobTitle;

    @Schema(description = "Phone number of the user", example = "+1234567890")
    @FormParam("phoneNumber")
    @PartType(MediaType.TEXT_PLAIN)
    public String phoneNumber;

    @Schema(description = "Address of the user", example = "123 Main St, City, Country")
    @FormParam("address")
    @PartType(MediaType.TEXT_PLAIN)
    public String address;

    @Schema(description = "Birth date of the user", example = "1990-01-01")
    @FormParam("birthDate")
    @PartType(MediaType.TEXT_PLAIN)
    public String birthDate;
}
