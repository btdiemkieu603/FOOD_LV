package com.kt.backend.dto;

import com.kt.backend.entity.AccountStatus;
import com.kt.backend.entity.Address;
import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Role;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class ResAccountDto {

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

	private Integer id;
	
	private String phonenumber;
	
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	private String email;
	
	private Role role;
	
	private Cart cart;
	
	private AccountStatus account_status;
	
	private Address address;
	
}
