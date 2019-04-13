package com.fullstack.backend.service;

import com.fullstack.backend.common.model.ListResponse;
import com.fullstack.backend.model.User;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUserById(Long id);

    User getUserById(Long id);

    ListResponse listUsers();

}
