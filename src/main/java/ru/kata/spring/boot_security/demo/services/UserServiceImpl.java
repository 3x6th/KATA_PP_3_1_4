package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return user.get();
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void add(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public User getUserByID(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
