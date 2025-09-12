package org.putri.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.putri.dto.user.UserDeleteDto;
import org.putri.dto.user.UserListDto;
import org.putri.dto.user.UserUpsertDto;
import org.putri.entity.User;
import org.putri.repository.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<UserListDto> getAllUser() {
        List<User> user = userRepository.find("deletedAt is null").list();
        return user.stream()
                .map(v -> modelMapper.map(v, UserListDto.class))
                .toList();
    }

    public void createUser(UserUpsertDto userUpsertDto) {
        LocalDate birthDate = LocalDate.parse(userUpsertDto.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        User user = new User();
        user.email = userUpsertDto.getEmail();
        user.password = BcryptUtil.bcryptHash(userUpsertDto.getPassword());
        user.username = userUpsertDto.getUsername();
        user.fullname = userUpsertDto.getFullname();
        user.address = userUpsertDto.getAddress();
        user.phoneNumber = userUpsertDto.getPhoneNumber();
        user.JobTitle = userUpsertDto.getJobTitle();
        user.profilePath = userUpsertDto.getProfilePath();
        user.birthDate = java.sql.Date.valueOf(birthDate);
        user.createdAt = new Date();

        userRepository.persist(user);
        userUpsertDto.setUserId(user.id);
    }

    public void updateUser(UserUpsertDto userUpsertDto, User user) {
        LocalDate birthDate = LocalDate.parse(userUpsertDto.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        user.email = userUpsertDto.getEmail();
        user.password = BcryptUtil.bcryptHash(userUpsertDto.getPassword());
        user.username = userUpsertDto.getUsername();
        user.fullname = userUpsertDto.getFullname();
        user.address = userUpsertDto.getAddress();
        user.phoneNumber = userUpsertDto.getPhoneNumber();
        user.JobTitle = userUpsertDto.getJobTitle();
        user.profilePath = userUpsertDto.getProfilePath();
        user.birthDate = java.sql.Date.valueOf(birthDate);
        user.updatedAt = new Date();
    }

    public void deleteUser(UserDeleteDto userDeleteDto, User user) {
        user.deletedAt = new Date();
        user.id = userDeleteDto.getUserId();
    }
}
