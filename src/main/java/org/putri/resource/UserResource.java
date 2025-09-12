package org.putri.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.putri.dto.user.UserDeleteDto;
import org.putri.dto.user.UserListDto;
import org.putri.dto.user.UserLoginDto;
import org.putri.dto.user.UserUpsertDto;
import org.putri.entity.User;
import org.putri.repository.UserRepository;
import org.putri.response.ApiResponse;
import org.putri.service.AuthService;
import org.putri.service.UserService;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    AuthService authService;

    @GET
    @Operation(summary = "Get All user", description = "Semua Detail user")
    @Path("/get-all-user")
    public Response getUser() {
        List<UserListDto> dtos = userService.getAllUser();
        return Response.ok(new ApiResponse<>("Success", 200, dtos)).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new user", description = "Membuat user baru dengan detail yang diberikan")
    @Transactional
    public Response createUser(@MultipartForm UserUpsertDto userDto) {
        User userCek = userRepository.find("email", userDto.getEmail()).firstResult();

        if (userCek != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ApiResponse<>("Email already exists, please use another emails", 404, null))
                    .build();
        }
        userService.createUser(userDto);
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
        userService.updateUser(userDto, user);
        return Response.ok(new ApiResponse<>("Success", 200, userDto)).build();
    }

    @DELETE
    @Transactional
    @Path("/delete")
    @Operation(summary = "Delete user", description = "Menghapus user yang sudah terdaftar")
    public Response deleteUser(UserDeleteDto userDeleteDto) {
        User user = userRepository.findById(userDeleteDto.getUserId());
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>("User not found", 404, null))
                    .build();
        }
        userService.deleteUser(userDeleteDto, user);
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

        String token = authService.genereateToken(user);
        // Response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("user", user); // optional, kalau mau kirim info user

        return Response.ok(new ApiResponse<>("Success", 200, responseBody)).build();
    }

    @GET
    @Path("/profile")
    @Operation(summary = "Get user profile", description = "Mengambil detail profile user yang sedang login")
    public Response getUserProfile(@QueryParam("userId") Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>("User not found", 404, null))
                    .build();
        }
        return Response.ok(new ApiResponse<>("Success", 200, user)).build();
    }

}
