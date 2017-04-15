package com.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.models.User;
import java.util.List;

public interface UserService extends UserDetailsService{

    public void addUser(User user);

    public User getUser(Long id);

    public User getUser(String username);

    public User getUserByEmail(String email);

    public void updateUser(User user);

    public void deleteUser(Long id);

    public List<User> getUsers();
}