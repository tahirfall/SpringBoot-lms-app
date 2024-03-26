package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.BookNotFoundException;
import com.esmt.misi2.lms.exceptions.UserNotFoundException;
import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserModel>> listUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id) {
        UserModel user = userService.findOne(id);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserModel> saveUser(@Valid @RequestBody UserModel user) {
        // Encrypt the password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }


    @PutMapping("/edit-user/{id}")
    public ResponseEntity<UserModel> editUser(@PathVariable Long id, @Valid @RequestBody UserModel user) {
        UserModel existingUser = userService.findOne(id);

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setEnabled(user.getEnabled());
            existingUser.setRole(user.getRole());

            return ResponseEntity.ok(userService.save(existingUser));
        } else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }



    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<List<UserModel>> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<UserModel> detailUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.fetchByIdWithLoans(id));
    }
}
