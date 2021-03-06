package com.bankproject.DAO;

import com.bankproject.objects.UserObject;
import com.bankproject.objects.UserOutputObject;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;

import java.sql.SQLException;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by bobyk on 04/05/16.
 */
public interface UserDAO {

    void addUser(UserObject usr) throws SQLException, DataFormatException;
    void deleteUser(UserObject usr) throws SQLException;
    List<UserObject> getAllUsers() throws SQLException;
    void updateUser(UserObject usr) throws SQLException, DataFormatException;
    UserObject getUserByUsername(String username) throws SQLException;
    UserObject getUserById(Long id) throws SQLException;
}
