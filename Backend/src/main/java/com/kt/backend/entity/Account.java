package com.kt.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Table(name = "account")
public class Account {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public AccountStatus getAccount_status() {
		return account_status;
	}

	public void setAccount_status(AccountStatus account_status) {
		this.account_status = account_status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String phonenumber;
	
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@OneToOne
    @JoinColumn(name = "cart_id")
	private Cart cart;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "account_status_id")
	private AccountStatus account_status;
	
	@OneToOne
    @JoinColumn(name = "address_id")
	private Address address;
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();	
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> reviews;
	
}