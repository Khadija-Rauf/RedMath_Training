package com.redmath.training.user;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository repository;
    private  JdbcTemplate jdbcTemplate;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUserName(String name)
    {

        return jdbcTemplate.queryForObject("select * form user where user_name = '" + name + "'", User.class);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + userName);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
    }
}
