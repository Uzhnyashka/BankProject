package com.bankproject.DAO;

import com.bankproject.objects.UserObject;
import org.springframework.security.access.annotation.Secured;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bobyk on 04/05/16.
 */
public interface UserDAO {

    void addUser(UserObject usr) throws SQLException;
    void deleteUser(UserObject usr) throws SQLException;
    List<UserObject> getAllUsers() throws SQLException;
    void updateUser(UserObject usr) throws SQLException;
    UserObject getUserByUsername(String username) throws SQLException;
}
