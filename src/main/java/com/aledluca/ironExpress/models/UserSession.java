package com.aledluca.ironExpress.models;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;


@Entity
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sessionId;
    @Column(unique=true)
    private String token;
    @Column(unique = true)
    private Integer userId;
    private String userType;
    private LocalDateTime sessionStartTime;
    private LocalDateTime sessionEndTime;


    public UserSession() {
    }

    public UserSession(String token, Integer userId, String userType, LocalDateTime sessionStartTime, LocalDateTime sessionEndTime) {
        this.token = token;
        this.userId = userId;
        this.userType = userType;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
    }

    public UserSession(Integer sessionId, String token, Integer userId, String userType, LocalDateTime sessionStartTime, LocalDateTime sessionEndTime) {
        this.sessionId = sessionId;
        this.token = token;
        this.userId = userId;
        this.userType = userType;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public LocalDateTime getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(LocalDateTime sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public LocalDateTime getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(LocalDateTime sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }
}
