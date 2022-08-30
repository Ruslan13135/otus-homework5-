package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Role;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.repositories.RoleRepository;
import ot.homework5plus.rushm.repositories.UserRepository;
import ot.homework5plus.rushm.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        log.debug("The application got in method - getAllUsers");
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        log.debug("The application got in method - getUser");
        return userRepository.getOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(List<String> userIds) {
        log.debug("The application got in method - getUsers");
        List<Long> ids = userIds.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
        return userRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public User addUser(User user) {
        log.debug("The application got in method - addUser");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        log.debug("The application got in method - updateUser");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.debug("The application got in method - deleteUser");
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount");
        return userRepository.count();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        Role userRole = roleRepository.findByRoleName("USER").orElse(null);
        assert userRole != null;
        user.setRoles(List.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("user")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        return Objects.nonNull(user) && passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return Optional.of(currentUserName);
    }
}
