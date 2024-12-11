package com.ngolajr.ecommerce.service;

import com.ngolajr.ecommerce.model.Role;
import com.ngolajr.ecommerce.model.User;
import com.ngolajr.ecommerce.model.dto.UserDto;
import com.ngolajr.ecommerce.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public User newUser(UserDto dto) {
        User user = new User();
        user.userDtoToUser(dto);
        //encoding the password from the dto.
        user.setPassword(encoder.encode(user.getPassword()));
        if(userRepository.findByUsername(dto.username()).isEmpty())
            return userRepository.save(user);

        return null;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAllUsersByRole(String role, Pageable pageable) {
        return userRepository.findAllByRolesContaining(Role.valueOf(role.toUpperCase()), pageable);
    }

    public User updateUser(long id, UserDto dto) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        Set<Role> roles = user.getRoles();
        user.userDtoToUser(dto);
        user.setId(id);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User promoteUser(long id) {
        Set<Role> newRoles = Set.of(Role.USER, Role.MANAGER);
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    public User demoteUser(long id) {
        Set<Role> newRoles = Set.of(Role.USER);
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
