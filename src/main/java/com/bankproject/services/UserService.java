package com.bankproject.services;

import com.bankproject.DAO.Impl.UserDAOImpl;
import com.bankproject.DAO.Impl.UserOutputDAOImpl;
import com.bankproject.objects.TestObject;
import com.bankproject.objects.UserObject;
import com.bankproject.objects.UserOutputObject;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 04/05/16.
 */

@Path("/users")
public class UserService {

    private UserDAOImpl userDAO = new UserDAOImpl();
    private UserOutputDAOImpl userOutputDAO = new UserOutputDAOImpl();

    @GET
    @Path("/list") //checked
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserOutputObject> getAllUsers(){
        List<UserOutputObject> users = new ArrayList<UserOutputObject>();
        if (CustomUserDetailService.getRole().equalsIgnoreCase("admin")){
            try {
                users = userOutputDAO.getAllUsers();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try{
                users.add(userOutputDAO.getUserByUsername(CustomUserDetailService.getUsername()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return users;
    }

    @POST
    @Path("/add")//checked
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserObject usr){

        try {
            userDAO.addUser(usr);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }
        String result = "Added new " + usr;
        return Response.status(201).entity(result).build();
    }

    @DELETE
    @Path("/{username}")//checked
    public Response deleteUser(@PathParam("username") String username){
        UserObject usr = null;
        try{
            usr = userDAO.getUserByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            userDAO.deleteUser(usr);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

        String result = "Deleted " + usr;
        return Response.status(201).entity(result).build();
    }

    @PUT
    @Path("/update")//checked
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UserObject usr){
        try{
            userDAO.updateUser(usr);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(403).build();
        }
        return Response.status(200).entity("Good").build();
    }

    @GET
    @Path("/get/{username}")//checked
    @Produces(MediaType.APPLICATION_JSON)
    public UserOutputObject getUserByUsername(@PathParam("username") String username){
        UserOutputObject user = null;
        try{
            user = userOutputDAO.getUserByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
}
