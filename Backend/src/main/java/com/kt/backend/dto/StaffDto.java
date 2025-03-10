package com.kt.backend.dto;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class StaffDto {
	private String email;  // Email nhân viên
    private String name;  // Tên nhân viên
    private Integer roleId;  // Mã vai trò
    private String phone;  // Số điện thoại
    private String gender;  // Giới tính
    private Integer age;  // Tuổi
    
    private String password; 
    private Integer accountStatusId;// Mật khẩu nhân viên
    //get set
    public Integer getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(Integer accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
    
	
	
}
