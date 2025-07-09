package com.example.EventManager.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    @Column(unique = true)
    private String username;
    
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name can only contain letters and spaces")
    private String fullname;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", 
    		message = "Email must be in valid format (e.g., user@gmail.com)")
    @Column(unique = true)
    private String email;
             
    @Lob
    @Column(name = "profile_image", columnDefinition = "MEDIUMBLOB")
    private byte[] profileImage;

   
    
   
	@NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
             message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String pass;
    
    private String role;
    
    // Default constructor
    public UserEntity() {}
    
//    // Constructor with parameters
//    public UserEntity(String username, String fullname, String email, String pass, String role) {
//        this.username = username;
//        this.fullname = fullname;
//        this.email = email;
//        this.pass = pass;
//        this.role = role;
//    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPass() {
        return pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    

    public UserEntity(Long id,
			@NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores") String username,
			@NotBlank(message = "Full name is required") @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name can only contain letters and spaces") String fullname,
			@NotBlank(message = "Email is required") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must be in valid format (e.g., user@gmail.com)") String email,
			@NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character") String pass,
			String role, byte[] profileImage) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.profileImage = profileImage;
		this.pass = pass;
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", fullname=" + fullname + ", email=" + email
				+ ", profileImagePath=" + profileImage + ", pass=" + pass + ", role=" + role + "]";
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	
}