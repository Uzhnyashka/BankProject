package com.bankproject.DAO.Impl;

import com.bankproject.DAO.OrderDAO;
import com.bankproject.DAO.UserDAO;
import com.bankproject.objects.OrderObject;
import com.bankproject.objects.UserObject;
import com.bankproject.services.CustomUserDetailService;
import com.bankproject.utils.HibernateUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;
import org.hibernate.dialect.H2Dialect;
import org.springframework.security.access.AccessDeniedException;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bobyk on 04/05/16.
 */
public class OrderDAOImpl implements OrderDAO{

    private UserDAOImpl userDAO = new UserDAOImpl();

    public void addOrder(OrderObject order) throws SQLException, AccessDeniedException {
        UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")){
            if (order.getUserId() == null) order.setUserId(user.getId());
            if (!order.getUserId().equals(user.getId())) throw new AccessDeniedException("Access Denied");
        }
        else {
            if (order.getUserId() == null) order.setUserId(user.getId());
        }

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "add() fail for Order", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateOrder(OrderObject order) throws SQLException, AccessDeniedException {
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")){
            UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
            if (!order.getUserId().equals(user.getId())) throw new AccessDeniedException("Access Denied");
        }

        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "update() fail for Order", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void deleteOrder(OrderObject order) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "delete() fail for Order", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public List<OrderObject> getAllOrders() throws SQLException {
        Session session = null;
        List<OrderObject> orders = new ArrayList<OrderObject>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            orders = session.createCriteria(OrderObject.class).list();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "getAll() fail for Orders", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }

        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")){
            System.out.println("user");
            List<OrderObject> ordersForUser = new ArrayList<OrderObject>();
            UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
            System.out.println(user.getUsername() + " " + user.getId());
            for(OrderObject order : orders){
                if (order.getUserId().equals(user.getId()) ||
                        order.getStatus().equalsIgnoreCase("publish")) ordersForUser.add(order);
            }
            return ordersForUser;
        }
        return orders;
    }

    @Override
    public List<OrderObject> getOrdersByUsername(String login) throws SQLException, AccessDeniedException {
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")
                && !CustomUserDetailService.getUsername().equalsIgnoreCase(login)) throw new AccessDeniedException("Access denied");
        UserObject user = userDAO.getUserByUsername(login);
        List<OrderObject> orderList = new ArrayList<OrderObject>();
        orderList = getAllOrders();

        List<OrderObject> ordersForUser = new ArrayList<OrderObject>();
        for (OrderObject order : orderList){
            if (order.getUserId().equals(user.getId())) ordersForUser.add(order);
        }
        return ordersForUser;
    }


    public OrderObject getOrderById(Long id) throws SQLException{
        Session session = null;
        OrderObject order = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order = (OrderObject) session.load(OrderObject.class, id);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "getOrderById() fail for Orders", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return order;
    }

}