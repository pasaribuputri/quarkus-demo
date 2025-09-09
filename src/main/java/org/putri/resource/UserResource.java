package org.putri.resource;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.putri.dto.user.UserDeleteDto;
import org.putri.dto.user.UserListDto;
import org.putri.dto.user.UserLoginDto;
import org.putri.dto.user.UserUpsertDto;
import org.putri.entity.User;
import org.putri.repository.UserRepository;
import org.putri.response.ApiResponse;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    @GET
    @Operation(summary = "Get All user", description = "Semua Detail user")
    @Path("/get-all-user")
    public Response getUser() {
        List<User> user = userRepository.find("deletedAt is null").list();
        List<UserListDto> dtos = user.stream()
                .map(v -> modelMapper.map(v, UserListDto.class))
                .toList();
        return Response.ok(new ApiResponse<>("Success", 200, dtos)).build();
    }

    @POST
    @Transactional
    @Path("/create")
    @Operation(summary = "Create new user", description = "Membuat user baru dengan detail yang diberikan")
    public Response createUser(UserUpsertDto userDto) {
        User userCek = userRepository.find("email", userDto.getEmail()).firstResult();

        if (userCek != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ApiResponse<>("Email already exists, please use another emails", 404, null))
                    .build();
        }

        User user = new User();
        user.email = userDto.getEmail();
        user.password = BcryptUtil.bcryptHash(userDto.getPassword());
        user.username = userDto.getUsername();
        user.createdAt = new Date();

        userRepository.persist(user);
        userDto.setUserId(user.id);

        return Response.ok(new ApiResponse<>("Success", 201, userDto)).build();
    }

    @PUT
    @Transactional
    @Path("/update")
    @Operation(summary = "Update user", description = "Mengubah user yang sudah terdaftar")
    public Response updateUser(UserUpsertDto userDto) {
        User user = userRepository.findById(userDto.getUserId());
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>("User not found", 404, null))
                    .build();
        }
        user.email = userDto.getEmail();
        user.password = BcryptUtil.bcryptHash(userDto.getPassword());
        user.username = userDto.getUsername();
        user.updatedAt = new Date();

        return Response.ok(new ApiResponse<>("Success", 200, userDto)).build();
    }

    @DELETE
    @Transactional
    @Path("/delete")
    @Operation(summary = "Delete user", description = "Menghapus user yang sudah terdaftar")
    public Response deleteUser(UserDeleteDto userDeleteDto) {
        User user = userRepository.findById(userDeleteDto.getUserId());
        user.deletedAt = new Date();
        return Response.ok(new ApiResponse<>("Succes", 200, userDeleteDto)).build();

    }

    @POST
    @Transactional
    @Path("/login")
    @Operation(summary = "Login user", description = "Login user dengan email dan password yang diberikan")
    public Response loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.find("email", userLoginDto.getEmail()).firstResult();

        if (user == null || !BcryptUtil.matches(userLoginDto.getPassword(), user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ApiResponse<>("Invalid email or password", 401, null))
                    .build();
        }

        // Generate JWT token
        String token = io.smallrye.jwt.build.Jwt.issuer("putri-app")
                .upn(user.email) // Subject
                .claim("id", user.id) // Custom claim: id
                .claim("username", user.username) // Custom claim: username
                .claim("email", user.email) // Custom claim: email
                .expiresIn(Duration.ofHours(2)) // Token valid 2 jam
                .sign(); // Sign dengan private key

        // Response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("user", user); // optional, kalau mau kirim info user

        return Response.ok(new ApiResponse<>("Success", 200, responseBody)).build();
    }

}
