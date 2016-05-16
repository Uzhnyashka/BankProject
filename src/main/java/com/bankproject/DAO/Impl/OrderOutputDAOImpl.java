package com.bankproject.DAO.Impl;

import com.bankproject.DAO.OrderOutputDAO;
import com.bankproject.objects.OrderObject;
import com.bankproject.objects.OrderOutputObject;
import com.bankproject.objects.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bobyk on 10/05/16.
 */

public class OrderOutputDAOImpl implements OrderOutputDAO {

    private OrderDAOImpl orderDAO = new OrderDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();

    List<OrderOutputObject> getOutputOrders(List<OrderObject> orders) throws SQLException{
        List<OrderOutputObject> ordersOutput = new ArrayList<OrderOutputObject>();

        for (OrderObject order : orders){
            UserObject user = userDAO.getUserById(order.getUserId());

            OrderOutputObject orderOutput = new OrderOutputObject();
            orderOutput.setId(order.getId());
            orderOutput.setName(user.getName());
            orderOutput.setPhone(user.getPhone());
            orderOutput.setAmount(order.getAmount());
            orderOutput.setCashType(order.getCashType());
            orderOutput.setOperationType(order.getOperationType());
            orderOutput.setCreationDate(order.getCreationDate());
            orderOutput.setStatus(order.getStatus());

            ordersOutput.add(orderOutput);
        }

        return ordersOutput;
    }

    @Override
    public List<OrderOutputObject> getAllOrders() throws SQLException {
        List<OrderObject> orders = orderDAO.getAllOrders();
        return getOutputOrders(orders);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderOutputObject> getOrdersByUsername(String username) throws SQLException {
        List<OrderObject> orders = orderDAO.getOrdersByUsername(username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> currentPrincipalName = authentication.getAuthorities();
        System.out.println(currentPrincipalName);
        return getOutputOrders(orders);
    }

    @Override
    public OrderOutputObject getOrderById(Long id) throws SQLException {
        OrderObject order = orderDAO.getOrderById(id);

        UserObject user = userDAO.getUserById(order.getUserId());

        OrderOutputObject orderOutput = new OrderOutputObject();
        orderOutput.setId(order.getId());
        orderOutput.setName(user.getName());
        orderOutput.setPhone(user.getPhone());
        orderOutput.setAmount(order.getAmount());
        orderOutput.setCashType(order.getCashType());
        orderOutput.setOperationType(order.getOperationType());
        orderOutput.setCreationDate(order.getCreationDate());
        orderOutput.setStatus(order.getStatus());

        return orderOutput;
    }
}
