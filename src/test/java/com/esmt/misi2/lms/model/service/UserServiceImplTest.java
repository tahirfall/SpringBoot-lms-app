package com.esmt.misi2.lms.model.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.esmt.misi2.lms.model.dao.IUsersDao;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserServiceImplTest {

    private IUserService userService;
    private IUsersDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = mock(IUsersDao.class);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void testSave() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("testUser");

        when(userDao.save(user)).thenReturn(user);

        UserModel savedUser = userService.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void testFindOne() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("testUser");

        when(userDao.findById(1L)).thenReturn(Optional.of(user));

        UserModel foundUser = userService.findOne(1L);

        assertEquals(user, foundUser);
    }

    @Test
    public void testDelete() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("testUser");

        userService.delete(1L);

        verify(userDao, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByUsername() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("testUser");

        when(userDao.findByUsername("testUser")).thenReturn(user);

        UserModel foundUser = userService.findByUsername("testUser");

        assertEquals(user, foundUser);
    }

    @Test
    public void testFetchByIdWithLoans() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("testUser");

        when(userDao.fetchByIdWithLoan(1L)).thenReturn(user);

        UserModel foundUser = userService.fetchByIdWithLoans(1L);

        assertEquals(user, foundUser);
    }


}

