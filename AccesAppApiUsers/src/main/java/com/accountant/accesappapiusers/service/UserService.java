package com.accountant.accesappapiusers.service;

import com.accountant.accesappapiusers.model.dto.UserDto;
import com.accountant.accesappapiusers.model.requests.UpdateUserRequestModel;
import com.accountant.accesappapiusers.model.responses.CreateUserResponseModel;
import com.accountant.accesappapiusers.model.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserDetailsByEmail(String email);

    UserDto getUserByUserId(String userId);

    UserDto getUserByUserIdWithAlbums(String userId);

    CreateUserResponseModel deleteUser(String userId);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(UpdateUserRequestModel user);
}
