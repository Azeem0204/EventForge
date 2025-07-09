package com.example.EventManager.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class RegisteredEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	String FullName, email, phone, event, Msg;
	
	// Add relationship to UserEntity
	@ManyToOne
	@JoinColumn(name = "user_id")
	
	private UserEntity user;
	
	// Add relationship to EventManager - This will store the actual event details
	@ManyToOne
	@JoinColumn(name = "event_id")
	private EventManager eventDetails;
	
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
	
	@Override
	public String toString() {
		return "RegisteredEvent [id=" + id + ", FullName=" + FullName + ", email=" + email + ", phone=" + phone
				+ ", event=" + event + ", Msg=" + Msg + "]";
	}
	
	public RegisteredEvent(int id, String fullName, String email, String phone, String event, String msg) {
		super();
		this.id = id;
		FullName = fullName;
		this.email = email;
		this.phone = phone;
		this.event = event;
		Msg = msg;
	}
	
	public RegisteredEvent() {
		
	}
}