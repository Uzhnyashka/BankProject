package com.bankproject.DAO.Impl;

import com.bankproject.DAO.UserOutputDAO;
import com.bankproject.objects.UserObject;
import com.bankproject.objects.UserOutputObject;
import com.bankproject.services.CustomUserDetailService;
import org.springframework.security.access.AccessDeniedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 08/05/16.
 */
public class UserOutputDAOImpl implements UserOutputDAO {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public List<UserOutputObject> getAllUsers() throws SQLException {
        List<UserObject> users = userDAO.getAllUsers();
        List<UserOutputObject> userOutput = new ArrayList<UserOutputObject>();

        for (UserObject usr : users){
            UserOutputObject user = new UserOutputObject();

            user.setName(usr.getName());
            user.setUsername(usr.getUsername());
            user.setPhone(usr.getPhone());
            user.setRole(usr.getRole());
            user.setId(usr.getId());

            userOutput.add(user);
        }

        return userOutput;
    }

    @Override
    public UserOutputObject getUserByUsername(String username) throws SQLException {

        if (CustomUserDetailService.getRole().equalsIgnoreCase("user") &&
                !CustomUserDetailService.getUsername().equals(username)){
            throw new AccessDeniedException("");
        }

        UserObject user = userDAO.getUserByUsername(username);
        UserOutputObject userOutput = new UserOutputObject();
        userOutput.setName(user.getName());
        userOutput.setPhone(user.getPhone());
        userOutput.setRole(user.getRole());
        userOutput.setUsername(user.getUsername());
        userOutput.setId(user.getId());

        return userOutput;
    }
}
