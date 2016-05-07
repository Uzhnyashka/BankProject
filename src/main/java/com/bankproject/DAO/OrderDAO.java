package com.bankproject.DAO;

import com.bankproject.objects.OrderObject;
import org.springframework.security.access.annotation.Secured;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by bobyk on 04/05/16.
 */
public interface OrderDAO {
    void addOrder(OrderObject order) throws SQLException;
    void updateOrder(OrderObject order) throws SQLException;
    void deleteOrder(OrderObject order) throws SQLException;
    List<OrderObject> getAllOrders() throws SQLException;
    List<OrderObject> getOrdersByUsername(String login) throws SQLException;
    OrderObject getOrderById(Long id) throws SQLException;
}
