package com.bankproject.services;

import com.bankproject.DAO.Impl.UserDAOImpl;
import com.bankproject.objects.UserObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bobyk on 04/05/16.
 */
public class CustomUserDetailService implements UserDetailsService {

    private UserDAOImpl userDAO = new UserDAOImpl();
    private UserObject userObject = null;
    private static UserDetails user;
    private static String role;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            userObject = userDAO.getUserByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userObject == null){
            throw new UsernameNotFoundException("username " + username + " not found");
        }
        Collection<GrantedAuthority> gr = new ArrayList<GrantedAuthority>();
        if (userObject.getRole().equals("admin")) {
            gr.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            role = "admin";
            System.out.println("admin");
        }
        else {
            gr.add(new SimpleGrantedAuthority("ROLE_USER"));
            role = "user";
            System.out.println("user");
        }
        user = new User(username, userObject.getPassword(), true, true, true, true, gr);
        return user;
    }

    public static String getUsername(){
        return user.getUsername();
    }
    public static String getRole() {return role;}
}
