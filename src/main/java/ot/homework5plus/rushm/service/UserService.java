package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    User getUser(Long userId);

    List<User> getUsers(List<String> userIds);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    long getCount();

    User saveUser(User user);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    Optional<String> getCurrentUser();
}
