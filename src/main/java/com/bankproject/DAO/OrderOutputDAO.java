package com.bankproject.DAO;

import com.bankproject.objects.OrderOutputObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bobyk on 10/05/16.
 */

public interface OrderOutputDAO {
    List<OrderOutputObject> getAllOrders() throws SQLException;
    List<OrderOutputObject> getOrdersByUsername(String username) throws SQLException;
    OrderOutputObject getOrderById(Long id) throws SQLException;
}
