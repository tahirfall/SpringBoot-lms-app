package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.IUsersDao;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    private IUserService userService;

    @MockBean
    private IUsersDao userDao;

    @Test
    void testFindAll() {
        // Simulate data
        UserModel user1 = new UserModel();
        UserModel user2 = new UserModel();
        when(userDao.findAll()).thenReturn(List.of(user1, user2));

        // Test
        assertEquals(2, userService.findAll().size());
    }

    @Test
    void testFindAllPaginable() {
        // Simulate data
        Page<UserModel> page = mock(Page.class);
        when(userDao.findAll(any(PageRequest.class))).thenReturn(page);

        // Test
        assertEquals(page, userService.findAll(PageRequest.of(0, 10)));
    }

    @Test
    void testSave() {
        // Simulate data
        UserModel user = new UserModel();
        when(userDao.save(user)).thenReturn(user);

        // Test
        assertEquals(user, userService.save(user));
    }

    @Test
    void testFindOne() {
        // Simulate data
        UserModel user = new UserModel();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));

        // Test
        assertEquals(user, userService.findOne(1L));
    }

    @Test
    void testDelete() {
        // Test
        userService.delete(1L);

        // Verify
        verify(userDao, times(1)).deleteById(1L);
    }

    @Test
    void testFindByUsername() {
        // Simulate data
        UserModel user = new UserModel();
        when(userDao.findByUsername("testuser")).thenReturn(user);

        // Test
        assertEquals(user, userService.findByUsername("testuser"));
    }

}
