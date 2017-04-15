package com.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.models.User;
import com.repositories.UserRepository;
import com.services.UserService;
import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        Optional<String> userName = this.getUsers()
                .stream()
                .map(a->a.getUsername())
                .filter(b->user.getUsername().equals(b))
                .findAny();
        try{
            userName.get();
            LOGGER.info("The user already exists");
            return;
        }catch(NoSuchElementException e){
            user.setEnabled(true);
            this.userRepository.save(user);
        }

        LOGGER.info("User [ " + user.getUsername() + " ] is added");
    }

    @Override
    public User getUser(Long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User account = null;
        try{
            account = getUser(username);
        } catch(UsernameNotFoundException e){
            LOGGER.info("No Such User. ",e);
        }

        if(account == null){
            LOGGER.warn("No such User");
            throw new UsernameNotFoundException("No such User [ " + username + " ]");
        }

        account.setLastAccessedDate(Calendar.getInstance().getTime());
        try{
            updateUser(account);
        } catch (UsernameNotFoundException e){
            LOGGER.info("No such User. ", e);
        }

        return account;
    }
}