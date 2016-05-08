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
        return Response.status(200).build();
    }

    @GET
    @Path("/{username}")
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

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderObject getOrderById(@PathParam("id") Long id){
        OrderObject order = null;
        try{
            order = orderDAO.getOrderById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id){
        OrderObject orderObject = null;
        try{
            orderObject = orderDAO.getOrderById(id);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(484).build();
        }
        if (orderObject == null) System.out.println(1337);
        else System.out.println(228);
        try {
            orderDAO.deleteOrder(orderObject);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(444).build();
        }

        String result = "Delete " + orderObject;
        return Response.status(200).entity(result).build();
    }
}
