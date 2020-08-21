package com.appsdeveloperblog.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);  // always findBY for searching, followed by Column name

    UserEntity findByUserId(String userId);
}
