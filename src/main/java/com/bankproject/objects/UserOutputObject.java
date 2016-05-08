package com.bankproject.objects;

/**
 * Created by bobyk on 08/05/16.
 */
public class UserOutputObject {

    private String name;
    private String username;
    private String role;
    private String phone;
    private Long id;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        return "User [" +
                this.name + " " +
                this.username + " " +
                this.phone + " " +
                this.role + "]";
    }
}
