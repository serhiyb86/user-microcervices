package com.accountant.accesappapiusers.data;

import com.accountant.accesappapiusers.model.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findUserEntityByUserId(String userId);

    List<UserEntity> findAll();

    void deleteByUserId(String userId);
}
