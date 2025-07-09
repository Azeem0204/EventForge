package com.example.EventManager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class EventManager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Lob
	private String img;
	
	private String category,location,organizer;
	
	@Column(name = "description", length = 10000)
	private String description;
	
	String name,date;
	private double price;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "EventManager [id=" + id + ", img=" + img + ", category=" + category + ", location=" + location
				+ ", organizer=" + organizer + ", description=" + description + ", name=" + name + ", date=" + date
				+ ", price=" + price + "]";
	}
	public EventManager(int id, String img, String category, String location, String organizer, String description,
			String name, String date, double price) {
		super();
		this.id = id;
		this.img = img;
		this.category = category;
		this.location = location;
		this.organizer = organizer;
		this.description = description;
		this.name = name;
		this.date = date;
		this.price = price;
	}
	public EventManager() {
		
	}
	
	
	
	

}
