package com.bankproject.DAO.Impl;

import com.bankproject.DAO.UserDAO;
import com.bankproject.objects.UserObject;
import com.bankproject.services.CustomUserDetailService;
import com.bankproject.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.*;
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
            JOptionPane.showMessageDialog(null, e.getMessage(), "add() fail for User", JOptionPane.OK_OPTION);
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
            JOptionPane.showMessageDialog(null, e.getMessage(), "delete() fail for User", JOptionPane.OK_OPTION);
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List<UserObject> getAllUsers() throws SQLException {
        Session session = null;
        List users = new ArrayList<UserObject>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(UserObject.class).list();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "getAllUsers() fail for User", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return users;
    }

    @Override
    public void updateUser(UserObject usr) throws SQLException, AccessDeniedException {
        Session session = null;
        UserObject currentUser = getUserByUsername(CustomUserDetailService.getUsername());
        if (!(CustomUserDetailService.getRole().equalsIgnoreCase("admin") ||
                usr.getUsername().equals(currentUser.getUsername()))) throw new AccessDeniedException("Access Denied");
        try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(usr);
                session.getTransaction().commit();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "update() fail for User", JOptionPane.OK_OPTION);
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public UserObject getUserByUsername(String username) throws SQLException {
        List<UserObject> users = (List<UserObject>) getAllUsers();
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
