package com.bankproject.objects;

import com.bankproject.enums.Status;

import java.util.Date;

/**
 * Created by bobyk on 10/05/16.
 */
public class OrderOutputObject {
    private Long id;
    private String name;
    private String phone;
    private String operationType;
    private String cashType;
    private Long amount;
    private Date creationDate;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operatioType) {
        this.operationType = operatioType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString(){
        return "Order [" + this.id + " " + this.name + " " +
                this.phone + " " + this.operationType + " " +
                this.amount + " " + this.cashType + " " +
                this.creationDate + " " + this.status + "]";
    }
}
