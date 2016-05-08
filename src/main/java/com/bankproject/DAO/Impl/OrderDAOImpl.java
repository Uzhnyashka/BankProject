package com.bankproject.DAO.Impl;

import com.bankproject.DAO.OrderDAO;
import com.bankproject.DAO.UserDAO;
import com.bankproject.objects.OrderObject;
import com.bankproject.objects.UserObject;
import com.bankproject.services.CustomUserDetailService;
import com.bankproject.utils.HibernateUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

    private OrderObject fillEmptyFields(OrderObject order, OrderObject newOrder){
        if (order.getUserId() == null) order.setUserId(newOrder.getUserId());
        if (order.getStatus() == null) order.setStatus(newOrder.getStatus());
        if (order.getAmount() == null) order.setAmount(newOrder.getAmount());
        if (order.getCashType() == null) order.setCashType(newOrder.getCashType());
        if (order.getCreationDate() == null) order.setCreationDate(newOrder.getCreationDate());
        if (order.getOperationType() == null) order.setOperationType(newOrder.getOperationType());

        return order;
    }

    public void updateOrder(OrderObject order) throws SQLException, AccessDeniedException {
        if (order.getId() == null){
            throw new NullPointerException("Fail data");
        }
        OrderObject newOrder = getOrderById(order.getId());
        order = fillEmptyFields(order, newOrder);

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

    public void deleteOrder(OrderObject order) throws SQLException, AccessDeniedException{
        UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user") && !user.getId().equals(order.getUserId())){
            throw new AccessDeniedException("Access denied");
        }

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
            List<OrderObject> ordersForUser = new ArrayList<OrderObject>();
            UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
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
        Session session = null;
        List<OrderObject> orders = new ArrayList<OrderObject>();
        UserObject user = userDAO.getUserByUsername(login);
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")
                && !CustomUserDetailService.getUsername().equalsIgnoreCase(login)) throw new AccessDeniedException("Access denied");
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction();
            Criteria cr = session.createCriteria(OrderObject.class);
            cr.add(Restrictions.eq("userId", user.getId()));
            orders = cr.list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return orders;
    }


    public OrderObject getOrderById(Long id) throws SQLException{
        Session session = null;
        OrderObject order = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order = (OrderObject) session.load(OrderObject.class, id);
            System.out.println(order);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "getOrderById() fail for Orders", JOptionPane.OK_OPTION);
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (order == null) return null;
        UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user") && !user.getId().equals(order.getUserId())){
            throw new AccessDeniedException("Access denied");
        }
        return order;
    }

}