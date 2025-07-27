package com.example.EventManager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Convert;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Entity
public class EventManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String img;

    private String category, location, organizer;

    @Column(name = "description", length = 10000)
    private String description;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    private double price;

    private int maxQuantity; // ✅ New field
    
    
    

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Getters and Setters
    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    // Add to existing constructor
    public EventManager(int id, String img, String category, String location, String organizer, String description,
                        String name, LocalDateTime date, double price, int maxQuantity) {
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
        this.maxQuantity = maxQuantity;
    }

    // toString
    @Override
    public String toString() {
        return "EventManager [id=" + id + ", img=" + img + ", category=" + category + ", location=" + location
                + ", organizer=" + organizer + ", description=" + description + ", name=" + name + ", date=" + date
                + ", price=" + price + ", maxQuantity=" + maxQuantity + "]";
    }

	public EventManager() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Default constructor remains unchanged
    
    
}
