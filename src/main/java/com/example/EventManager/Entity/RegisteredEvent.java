package com.example.EventManager.Entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class RegisteredEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String FullName;
    private String email;
    private String phone;
    private String event;
    private String Msg;

    @Column(name = "registered_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime registeredDate;

    @Column(name = "modified_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventManager eventDetails;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EventManager getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventManager eventDetails) {
        this.eventDetails = eventDetails;
    }

    // JPA lifecycle hooks

    @PrePersist
    protected void onCreate() {
        this.registeredDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    // Constructors

    public RegisteredEvent() {
    }

    public RegisteredEvent(int id, String fullName, String email, String phone, String event, String msg) {
        super();
        this.id = id;
        this.FullName = fullName;
        this.email = email;
        this.phone = phone;
        this.event = event;
        this.Msg = msg;
    }

    @Override
    public String toString() {
        return "RegisteredEvent [id=" + id + ", FullName=" + FullName + ", email=" + email +
               ", phone=" + phone + ", event=" + event + ", Msg=" + Msg +
               ", registeredDate=" + registeredDate + ", modifiedDate=" + modifiedDate + "]";
    }
}
