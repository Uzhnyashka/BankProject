package com.bankproject.DAO;

import com.bankproject.objects.UserOutputObject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bobyk on 08/05/16.
 */
public interface UserOutputDAO {
    List<UserOutputObject> getAllUsers() throws SQLException;
    UserOutputObject getUserByUsername(String username) throws SQLException;
}
