package com.bankproject.services;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.bankproject.DAO.Impl.OrderDAOImpl;
import com.bankproject.DAO.Impl.UserDAOImpl;
import com.bankproject.objects.OrderObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 27/04/16.
 */

@Path("/orders")
public class OrderService {

    private static OrderDAOImpl orderDAO = new OrderDAOImpl();
    private static UserDAOImpl userDAO = new UserDAOImpl();

    @GET//get all orders
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderObject> getAllOrders(){
        List<OrderObject> orders = new ArrayList<OrderObject>();
        try{
            orders = orderDAO.getAllOrders();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    @POST//add order
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrder(OrderObject order) throws SQLException, AccessDeniedException{
        try{
            orderDAO.addOrder(order);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }
        String result = "Add " + order;
        System.out.println(result);
        return Response.status(201).entity(result).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(OrderObject order){
        try{
            orderDAO.updateOrder(order);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("/get/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderObject> getOrdersForUser(@PathParam("username") String username){
        List<OrderObject> orders = new ArrayList<OrderObject>();
        try{
            orders = orderDAO.getOrdersByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }
}
