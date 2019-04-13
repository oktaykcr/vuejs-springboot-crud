package com.fullstack.backend.service;

import com.fullstack.backend.common.model.ListResponse;
import com.fullstack.backend.exception.ApiError;
import com.fullstack.backend.exception.ExceptionEnum;
import com.fullstack.backend.model.User;
import com.fullstack.backend.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private User createUser() {
        User user = new User("test", "user", 23);
        user.setId(1l);
        return user;
    }

    @Test
    public void createUser_shouldCreateUser() {
        User user = createUser();
        Mockito.when(repository.save(user)).thenReturn(user);

        User createdUser = service.createUser(user);

        assertThat(createdUser).isEqualTo(user);
    }

    @Test
    public void createUser_shouldBeError_userIsNull() throws ApiError {
        User user = null;
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.createUser(user);
    }

    @Test
    public void createUser_shouldBeError_firstnameIsNull() throws ApiError {
        User user = createUser();
        user.setFirstname(null);
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.createUser(user);
    }

    @Test
    public void createUser_shouldBeError_lastnameIsNull() throws ApiError {
        User user = createUser();
        user.setLastname(null);
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.createUser(user);
    }

    @Test
    public void createUser_shouldBeError_ageIsNull() throws ApiError {
        User user = createUser();
        user.setAge(null);
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.createUser(user);
    }

    @Test
    public void updateUser_shouldUpdateUser() {
        User updatedUser = createUser();
        updatedUser.setFirstname("updatedTest");
        User oldUser = createUser();

        Mockito.when(repository.getOne(updatedUser.getId())).thenReturn(oldUser);
        Mockito.when(repository.save(updatedUser)).thenReturn(updatedUser);

        service.updateUser(updatedUser);

        assertThat(updatedUser.getFirstname()).isEqualTo(oldUser.getFirstname());
    }

    @Test
    public void updateUser_shouldBeError_userIsNull() throws ApiError {
        User user = null;
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.updateUser(user);
    }

    @Test
    public void updateUser_shouldBeError_userIdIsNull() throws ApiError {
        User user = createUser();
        user.setId(null);
        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.updateUser(user);
    }

    @Test
    public void updateUser_shouldBeError_updatedUserIsNull() throws ApiError {

        User user = createUser();

        Mockito.when(repository.getOne(Mockito.anyLong())).thenReturn(null);

        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.NOT_FOUND.getErrorCode()));

        service.updateUser(user);
    }

    @Test
    public void deleteUserById_shouldDeleteUser() {
        User user = createUser();
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        boolean result = service.deleteUserById(1l);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void deleteUserById_shouldBeError_idIsNull() {
        Long id = null;

        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.deleteUserById(id);
    }

    @Test
    public void deleteUserById_shouldBeFalse_wantToDeleteUserIsNotPresent() {
        Optional<User> user = Optional.empty();
        Long id = 1l;
        Mockito.when(repository.findById(id)).thenReturn(user);

        boolean result = service.deleteUserById(id);

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void getUserById_shouldGetUser() {
        User user = createUser();
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        User foundUser = service.getUserById(1l);
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    public void getUserById_shouldBeError_idIsNull() {
        Long id = null;

        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.BAD_REQUEST.getErrorCode()));

        service.getUserById(id);
    }

    @Test
    public void getUserById_shouldBeError_wantToGetUserIsNotPresent() {
        Optional<User> user = Optional.empty();
        Long id = 1l;
        Mockito.when(repository.findById(id)).thenReturn(user);

        expectedException.expect(ApiError.class);
        expectedException.expectMessage(CoreMatchers.equalTo(ExceptionEnum.NOT_FOUND.getErrorCode()));

        service.getUserById(id);
    }

    @Test
    public void listUsers_shouldList() {
        List<User> users = Arrays.asList(createUser(), createUser());
        ListResponse listResponse = new ListResponse();
        listResponse.setData(users);
        listResponse.setTotalCount(users.size());

        Mockito.when(repository.findAll()).thenReturn(users);

        ListResponse response = service.listUsers();

        assertThat(response.getData()).isEqualTo(listResponse.getData());
        assertThat(response.getTotalCount()).isEqualTo(listResponse.getTotalCount());
    }


}
