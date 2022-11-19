package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserDetailsImpl implements UserDetailsService {

    final UserDao userDao;

    public UserDetailsImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userPrimary = Optional.ofNullable(userDao.findByName(username));
        if (!userPrimary.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(userPrimary.get().getUsername(),
                userPrimary.get().getPassword(), ath(userPrimary.get().getRoles()));
        return userPrimary.get();
    }

    private Collection<? extends GrantedAuthority> ath(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());
    }
}
