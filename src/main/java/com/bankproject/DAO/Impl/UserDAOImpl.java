package com.bankproject.DAO.Impl;

import com.bankproject.DAO.UserDAO;
import com.bankproject.objects.UserObject;
import com.bankproject.objects.UserOutputObject;
import com.bankproject.services.CustomUserDetailService;
import com.bankproject.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.*;
import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 04/05/16.
 */

public class UserDAOImpl implements UserDAO {

    private String encodePassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public void addUser(UserObject usr) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            usr.setPassword(encodePassword(usr.getPassword()));
            session.save(usr);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new SQLException("Failed data");
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public void deleteUser(UserObject usr) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(usr);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new NullPointerException("");
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List<UserObject> getAllUsers() throws SQLException {
        Session session = null;
        List<UserObject> users = new ArrayList<UserObject>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(UserObject.class).list();
        }catch (Exception e){
            throw new SQLException("");
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }

        return users;
    }

    private UserObject fillEmptyFields(UserObject user, UserObject newUser){
        System.out.println(newUser);
        System.out.println(user);
        if (user.getId() == null) user.setId(newUser.getId());
        if (user.getUsername() == null) user.setUsername(newUser.getUsername());
        if (user.getRole() == null) user.setRole(newUser.getRole());
        if (user.getName() == null) user.setName(newUser.getName());
        if (user.getPhone() == null) user.setPhone(newUser.getPhone());
        if (user.getPassword() != null){
            user.setPassword(encodePassword(user.getPassword()));
        }
        else user.setPassword(newUser.getPassword());

        System.out.println(user);
        return user;
    }

    @Override
    public void updateUser(UserObject usr) throws SQLException, AccessDeniedException {
        Session session = null;
        UserObject currentUser = getUserByUsername(CustomUserDetailService.getUsername());
        if (!(CustomUserDetailService.getRole().equalsIgnoreCase("admin") ||
                usr.getUsername().equals(currentUser.getUsername()))) throw new AccessDeniedException("Access Denied");
        UserObject newUser = getUserByUsername(usr.getUsername());
        usr = fillEmptyFields(usr, newUser);
        try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(usr);
                session.getTransaction().commit();
        }
        catch (Exception e){
            throw new SQLException("");
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public UserObject getUserByUsername(String username) throws SQLException {
        List<UserObject> users = getAllUsers();
        UserObject userObject = null;

        for (UserObject usr : users){
            if (username.equalsIgnoreCase(usr.getUsername())){
                userObject = usr;
                break;
            }
        }
        return userObject;
    }
}
