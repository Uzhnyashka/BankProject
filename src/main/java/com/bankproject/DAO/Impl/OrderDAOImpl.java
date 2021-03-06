package com.bankproject.DAO.Impl;

import com.bankproject.DAO.OrderDAO;
import com.bankproject.objects.OrderObject;
import com.bankproject.enums.Status;
import com.bankproject.objects.UserObject;
import com.bankproject.services.CustomUserDetailService;
import com.bankproject.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.AccessDeniedException;

/*import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;*/
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

/**
 * Created by bobyk on 04/05/16.
 */

//@Transactional(readOnly = true)
public class OrderDAOImpl implements OrderDAO{

    private UserDAOImpl userDAO = new UserDAOImpl();
  /*  ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    Validator validator = vf.getValidator();*/

    private boolean normalData(OrderObject order) throws SQLException{
        Status status = order.getStatus();
        String operationType = order.getOperationType();
        Long amount = order.getAmount();
        String cashType = order.getCashType();
        Long userId = order.getUserId();
        System.out.println(operationType);
        if (!operationType.equalsIgnoreCase("sell") && !operationType.equalsIgnoreCase("buy")) return false;
        System.out.println(amount);
        if (amount < 0) return false;
        System.out.println(cashType);
        if (!(cashType.equalsIgnoreCase("USD") ||
                cashType.equalsIgnoreCase("EUR") ||
                cashType.equalsIgnoreCase("UAH") ||
                cashType.equalsIgnoreCase("RUB"))) return false;

        List<UserObject> users = userDAO.getAllUsers();
        boolean find = false;
        for (UserObject user : users){
            if (user.getId().equals(userId)) {
                find = true;
                break;
            }
        }
        return find;
    }

 /*   public static void validate(Object object, Validator validator) {
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        System.out.println(object);
        System.out.println(String.format("Кол-во ошибок: %d",
                constraintViolations.size()));

        for (ConstraintViolation<Object> cv : constraintViolations)
            System.out.println(String.format(
                    "Внимание, ошибка! property: [%s], value: [%s], message: [%s]",
                    cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
    }*/

  //  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addOrder(OrderObject order) throws SQLException, AccessDeniedException, DataFormatException {
        UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")){
            if (order.getUserId() == null) order.setUserId(user.getId());
            if (!order.getUserId().equals(user.getId())) throw new AccessDeniedException("Access Denied");
        }
        else {
            if (order.getUserId() == null) order.setUserId(user.getId());
        }

       // validate(order, validator);

    /*    System.out.println(order);
        if (!normalData(order)) {
            throw new DataFormatException();
        }*/

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
            //getHibernateTemplate().save(order);
        }catch (Exception e){
            throw new SQLException();
        }finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    private OrderObject fillEmptyFields(OrderObject order, OrderObject newOrder){
        if (order.getUserId() != null) {
            throw new AccessDeniedException("");
        }else order.setUserId(newOrder.getUserId());
        if (order.getStatus() == null) order.setStatus(newOrder.getStatus());
        if (order.getAmount() == null) order.setAmount(newOrder.getAmount());
        if (order.getCashType() == null) order.setCashType(newOrder.getCashType());
        if (order.getCreationDate() == null) order.setCreationDate(newOrder.getCreationDate());
        if (order.getOperationType() == null) order.setOperationType(newOrder.getOperationType());

        return order;
    }

    public void updateOrder(OrderObject order) throws SQLException, AccessDeniedException, DataFormatException {
        if (order.getId() == null){
            throw new DataFormatException("Fail data");
        }
        OrderObject newOrder = getOrderById(order.getId());
        order = fillEmptyFields(order, newOrder);

        if (CustomUserDetailService.getRole().equalsIgnoreCase("user")){
            UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
            if (!order.getUserId().equals(user.getId())) throw new AccessDeniedException("Access Denied");
        }

        if (!normalData(order)){
            throw new DataFormatException("Fail data");
        }

        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new SQLException();
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
            throw new SQLException();
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
            throw new SQLException();
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
                        order.getStatus() == Status.publish) ordersForUser.add(order);
            }
            return ordersForUser;
        }
        return orders;
    }

    @Override
    public List<OrderObject> getOrdersByUsername(String login) throws SQLException {
        Session session = null;
        List<OrderObject> orders = new ArrayList<OrderObject>();
        UserObject user = userDAO.getUserByUsername(login);
       /* if (CustomUserDetailService.getRole().equalsIgnoreCase("user")
                && !CustomUserDetailService.getUsername().equalsIgnoreCase(login)) throw new AccessDeniedException("Access denied");*/
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


    public OrderObject getOrderById(Long id) throws SQLException, AccessDeniedException{
        List<OrderObject> orders = getAllOrders();
        UserObject user = userDAO.getUserByUsername(CustomUserDetailService.getUsername());
        for (OrderObject order : orders){
            if (order.getId().equals(id)) {
                if (CustomUserDetailService.getRole().equalsIgnoreCase("user") && !user.getId().equals(order.getUserId())){
                    throw new AccessDeniedException("Access denied");
                }
                return order;
            }
        }
        return null;
    }

}