package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.UserNotFoundException;
import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/list-users")
    public List<UserModel> listUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @PostMapping("/create-user")
    public UserModel saveUser(@Valid @RequestBody UserModel user) {
        // Encrypt the password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setEnabled(true);
        // Set default role (ROLE_USER)
        // user.setRole(UserRole.ROLE_USER);
        //user.setRole(user.getRole());
        //user.setEnabled(true);
        return userService.save(user);
    }


    @PutMapping("/edit-user/{id}")
    public UserModel editUser(@PathVariable Long id, @Valid @RequestBody UserModel user) {
        UserModel existingUser = userService.findOne(id);

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setEnabled(user.getEnabled());
            existingUser.setRole(user.getRole());

            return userService.save(existingUser);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }



    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/detail/{id}")
    public UserModel detailUser(@PathVariable Long id) {
        return userService.fetchByIdWithLoans(id);
    }
}
