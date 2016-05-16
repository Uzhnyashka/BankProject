package com.bankproject.DAO;

import com.bankproject.objects.OrderObject;
import org.springframework.security.access.annotation.Secured;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by bobyk on 04/05/16.
 */
public interface OrderDAO {

    void addOrder(OrderObject order) throws SQLException, DataFormatException;
    void updateOrder(OrderObject order) throws SQLException, DataFormatException;
    void deleteOrder(OrderObject order) throws SQLException;
    List<OrderObject> getAllOrders() throws SQLException;
    @Secured(value = {"ROLE_ADMIN"})
    List<OrderObject> getOrdersByUsername(String login) throws SQLException;
    OrderObject getOrderById(Long id) throws SQLException;
}
