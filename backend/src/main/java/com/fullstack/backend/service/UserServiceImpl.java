package com.fullstack.backend.service;

import com.fullstack.backend.common.model.ListResponse;
import com.fullstack.backend.exception.ApiError;
import com.fullstack.backend.exception.ExceptionEnum;
import com.fullstack.backend.exception.ExceptionFactory;
import com.fullstack.backend.model.User;
import com.fullstack.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(User user) {
        if(user == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "All Fields");
        }

        if(user.getFirstname() == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "firstname");
        }
        if(user.getLastname() == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "lastname");
        }
        if(user.getAge() == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "age");
        }
        user.setCreatedDate(new Date());
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if(user == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "All Fields");
        }

        if(user.getId() == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "userId");
        }

        User updatedUser = repository.getOne(user.getId());

        if(updatedUser == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.NOT_FOUND, "user");
        }

        if(user.getFirstname() != null) {
            updatedUser.setFirstname(user.getFirstname());
        }

        if(user.getLastname() != null) {
            updatedUser.setLastname(user.getLastname());
        }

        if(user.getAge() != null) {
            updatedUser.setAge(user.getAge());
        }

        updatedUser.setUpdatedDate(new Date());

        return repository.save(updatedUser);
    }

    @Override
    public boolean deleteUserById(Long id) {
        if(id == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "userId");
        }
        Optional<User> user = repository.findById(id);
        if(!user.isPresent()) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public User getUserById(Long id) throws ApiError {
        if(id == null) {
            throw ExceptionFactory.getApiError(ExceptionEnum.BAD_REQUEST, "userId");
        }
        try {
            Optional<User> user = repository.findById(id);
            if(!user.isPresent()) {
                throw ExceptionFactory.getApiError(ExceptionEnum.NOT_FOUND, "user");
            }
            return user.get();
        } catch (ApiError error) {
            throw error;
        }
    }

    @Override
    public ListResponse listUsers() {
        ListResponse response = new ListResponse();
        List<User> users = repository.findAll();
        response.setData(users);
        response.setTotalCount(users.size());
        return response;
    }
}
