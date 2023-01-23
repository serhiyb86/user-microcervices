package com.accountant.accesappapiusers.util;

import com.accountant.accesappapiusers.model.dto.UserDto;
import com.accountant.accesappapiusers.model.responses.CreateUserResponseModel;
import com.accountant.accesappapiusers.model.responses.UserResponseModel;
import com.accountant.accesappapiusers.model.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class UserMapper extends ModelMapper {
    //   private final ModelMapper mapper;

    public UserMapper() {
        super();
        //   this.mapper = new ModelMapper();
        this.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setSkipNullEnabled(true);
    }

    public UserDto toUserDto(Object source) {
        return this.map(source, UserDto.class);
    }

    public UserEntity toUserEntity(Object source) {
        return this.map(source, UserEntity.class);
    }

    public CreateUserResponseModel toCreateUserResponseModel(Object source) {
        return this.map(source, CreateUserResponseModel.class);
    }

    public UserResponseModel toUserResponseModel(Object source) {
        return this.map(source, UserResponseModel.class);
    }
}
