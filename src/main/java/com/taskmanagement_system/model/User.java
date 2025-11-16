package com.taskmanagement_system.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @Column(nullable = false,name = "use_name")
    private String userName;
    @Column(name = "pass_word",nullable = false)
    private String passWord;
    @Column(name = "user_email",unique = true,nullable=false)
    private String email;
    @Column(name = "mobile_number",nullable = false,unique = true)
    private String mobileNumber;
    @Column(name = "alternative_mobile_number",nullable = false)
    private String alternativeMobileNumber;
    @Column(name = "user_address")
    private String address;

    @Column(name = "user_role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "user_is_active",nullable = false)
    private boolean isActive;
    @Column(name = "user_created_time")
    private LocalDateTime createdAt;
    @Column(name = "last_updated_time")
    private LocalDateTime updatedAt;


    @PrePersist
    public  void  onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
        this.isActive=true;
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

    public User() {
    }

    public User(Integer id, String userName, String passWord, String email, String mobileNumber, String alternativeMobileNumber, String address, Role role, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.alternativeMobileNumber = alternativeMobileNumber;
        this.address = address;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAlternativeMobileNumber() {
        return alternativeMobileNumber;
    }

    public void setAlternativeMobileNumber(String alternativeMobileNumber) {
        this.alternativeMobileNumber = alternativeMobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", alternativeMobileNumber='" + alternativeMobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
