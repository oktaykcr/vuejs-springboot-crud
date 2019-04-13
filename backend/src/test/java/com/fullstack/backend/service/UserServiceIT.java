package com.fullstack.backend.service;

import com.fullstack.backend.common.model.ListResponse;
import com.fullstack.backend.model.User;
import com.fullstack.backend.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceIT {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Test
    public void createUser() {
        User user = new User("Test", "User", 23);
        service.createUser(user);

        User foundUser = repository.getOne(user.getId());

        assertThat(foundUser.getFirstname()).isEqualTo(user.getFirstname());
        assertThat(foundUser.getLastname()).isEqualTo(user.getLastname());
        assertThat(foundUser.getAge()).isEqualTo(user.getAge());
        assertThat(foundUser.getCreatedDate()).isNotNull();
        repository.deleteById(foundUser.getId());
    }

    @Test
    public void updateUser() {
        User user = new User("Test", "User", 23);

        User createdUser = service.createUser(user);

        User updateUserFirstname = new User();
        updateUserFirstname.setId(createdUser.getId());
        updateUserFirstname.setFirstname("updatedTest");

        User updatedUser = service.updateUser(updateUserFirstname);

        assertThat(updatedUser.getFirstname()).isEqualTo(updateUserFirstname.getFirstname());
        assertThat(updatedUser.getLastname()).isEqualTo(createdUser.getLastname());
        assertThat(updatedUser.getAge()).isEqualTo(createdUser.getAge());
        assertThat(updatedUser.getUpdatedDate()).isNotNull();

        repository.deleteById(updatedUser.getId());
    }

    @Test
    public void deleteUserById() {
        User user = new User("Test", "User", 23);

        User createdUser = service.createUser(user);

        boolean result = service.deleteUserById(createdUser.getId());

        assertThat(result).isEqualTo(true);

    }

    @Test
    public void getUserById() {
        User user = new User("Test", "User", 23);
        User createdUser = service.createUser(user);

        User foundUser = service.getUserById(createdUser.getId());

        assertThat(foundUser.getFirstname()).isEqualTo(createdUser.getFirstname());
        assertThat(foundUser.getLastname()).isEqualTo(createdUser.getLastname());
        assertThat(foundUser.getAge()).isEqualTo(createdUser.getAge());
    }

    @Test
    public void listUsers() {
        User user = new User("Test", "User", 23);
        User createdUser = service.createUser(user);
        User user2 = new User("Test2", "User2", 20);
        User createdUser2 = service.createUser(user2);

        List<User> users = Arrays.asList(createdUser, createdUser2);


        ListResponse listResponse = service.listUsers();

        List<User> data = listResponse.getData();
        Integer totalCount = listResponse.getTotalCount();

        assertThat(data.get(0).getFirstname()).isEqualTo(users.get(0).getFirstname());
        assertThat(data.get(1).getFirstname()).isEqualTo(users.get(1).getFirstname());
        assertThat(totalCount).isEqualTo(users.size());

        repository.deleteById(createdUser.getId());
        repository.deleteById(createdUser2.getId());
    }

}
