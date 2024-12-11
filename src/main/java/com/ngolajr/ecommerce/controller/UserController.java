package com.ngolajr.ecommerce.controller;
import com.ngolajr.ecommerce.model.User;
import com.ngolajr.ecommerce.model.dto.UserDto;
import com.ngolajr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> newUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.newUser(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> FindUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findUserById(Long.parseLong(id)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> FindUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<User>> FindAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(pageable));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> FindUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Page<User>> findUserByRole(@PathVariable String role, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsersByRole(role, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(Long.parseLong(id), userDto));
    }

    @PutMapping("/{id}/promote")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<User> promoteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.promoteUser(Long.parseLong(id)));
    }

    @PutMapping("/{id}/demote")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<User> demoteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.demoteUser(Long.parseLong(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(Long.parseLong(id));
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

}
