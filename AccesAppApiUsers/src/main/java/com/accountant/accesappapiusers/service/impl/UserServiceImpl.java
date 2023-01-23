package com.accountant.accesappapiusers.service.impl;

import com.accountant.accesappapiusers.data.AlbumsServiceClient;
import com.accountant.accesappapiusers.data.UserRepository;
import com.accountant.accesappapiusers.model.dto.UserDto;
import com.accountant.accesappapiusers.model.requests.UpdateUserRequestModel;
import com.accountant.accesappapiusers.model.responses.AlbumResponseModel;
import com.accountant.accesappapiusers.model.responses.CreateUserResponseModel;
import com.accountant.accesappapiusers.model.user.UserEntity;
import com.accountant.accesappapiusers.service.UserService;
import com.accountant.accesappapiusers.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private AlbumsServiceClient albumsServiceClient;
    @Value("${albums.url}")
    private String albumsUrl;

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(encoder.encode(userDetails.getPassword()));
        UserEntity userEntity = mapper.toUserEntity(userDetails);
        UserEntity savedEntity = userRepository.save(userEntity);
        return mapper.toUserDto(savedEntity);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);
        return mapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity user = getUserEntityByUserId(userId);
        return mapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUserIdWithAlbums(String userId) {
        UserDto user = getUserByUserId(userId);

        logger.info("Before calling albums Microservice.");
        List<AlbumResponseModel> albums = albumsServiceClient.getAlbums(userId);
        logger.info("After calling albums Microservice.");
        user.setAlbums(albums);
        return user;
    }

    @Transactional
    @Override
    public CreateUserResponseModel deleteUser(String userId) {
        UserDto user = getUserByUserId(userId);
        userRepository.deleteByUserId(userId);
        return mapper.toCreateUserResponseModel(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> all = userRepository.findAll();
        return all == null ? new ArrayList<>() : all;
    }

    @Transactional
    @Override
    public UserEntity updateUser(UpdateUserRequestModel user) {
        String userId = user.getUserId();
        UserEntity userEntity;
        try {
            userEntity = getUserEntityByUserId(userId);
        }
        catch (UsernameNotFoundException e) {
            logger.info("User entity id: {} is not defined. Perform user created.", userId);
            UserDto userCreated = createUser(mapper.toUserDto(user));
            userId = userCreated.getUserId();
            userEntity = getUserEntityByUserId(userId);
            return userEntity;
        }
        mapper.map(user, userEntity);
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    private UserEntity getUserEntityByUserId(String userId) {
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found." + userId);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " is not found.");
        return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
    }
}
