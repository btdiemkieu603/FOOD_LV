package com.kt.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "staff")
public class Staff {

	 @Id
	    @Column(name = "staff_email", nullable = false, unique = true)
	    private String email;  // Email nhân viên

	    @Column(name = "name")
	    private String name;  // Tên nhân viên

	    @ManyToOne
	    @JoinColumn(name = "role_id")
	    private Role role;  // Vai trò (khóa ngoại)

	    @Column(name = "phone")
	    private String phone;  // Số điện thoại

	    @Column(name = "gender")
	    private String gender;  // Giới tính

	    @Column(name = "age")
	    private Integer age;  // Tuổi
	    
	    // Thêm thuộc tính mật khẩu
	    @JsonIgnore  // Bỏ qua mật khẩu khi serializing thành JSON
	    @Column(name = "password", nullable = false)
	    private String password;  // Mật khẩu nhân viên
	    
	    @ManyToOne
	    @JoinColumn(name = "account_status_id", nullable = false)
	    private AccountStatus accountStatus; // Liên kết với trạng thái tài khoản
//get set

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public AccountStatus getAccountStatus() {
			return accountStatus;
		}

		public void setAccountStatus(AccountStatus accountStatus) {
			this.accountStatus = accountStatus;
		}

	    
	
}