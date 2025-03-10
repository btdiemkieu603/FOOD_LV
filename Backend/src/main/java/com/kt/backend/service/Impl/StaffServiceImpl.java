package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.dto.StaffDto;
import com.kt.backend.dto.UpdateAccountDto;
import com.kt.backend.dto.CartDto;
import com.kt.backend.dto.ChangePasswordDto;
import com.kt.backend.dto.LoginDto;

import com.kt.backend.dto.AddressDto;
import com.kt.backend.entity.Staff;
import com.kt.backend.entity.Staff;
//import com.kt.backend.entity.Address;
//import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Role;
import com.kt.backend.exception.ResourceNotFoundException;

import com.kt.backend.repository.AccountStatusRepository;
import com.kt.backend.repository.RoleRepository;
import com.kt.backend.repository.StaffRepository;

import com.kt.backend.service.AddressService;
import com.kt.backend.service.CartService;
import com.kt.backend.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {

	 @Autowired
	    private StaffRepository staffRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private ModelMapper modelMapper;

//	    @Autowired
//	    private PasswordEncoder passwordEncoder;  // Tiêm PasswordEncoder

//	    @Override
//	    public StaffDto createStaff(StaffDto staffDto) {
//	        Staff staff = modelMapper.map(staffDto, Staff.class);
//	        
//	        Role role = roleRepository.findById(staffDto.getRoleId())
//	                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", staffDto.getRoleId()));
//	        staff.setRole(role);
//
//	        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
//	        if (staffDto.getPassword() != null) {  // Kiểm tra xem mật khẩu có hợp lệ không
//	            staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
//	        }
//	        
//	        Staff savedStaff = staffRepository.save(staff);
//	        return modelMapper.map(savedStaff, StaffDto.class);
//	    }

	    
	    @Override
	    public StaffDto createStaff(StaffDto staffDto) {
	    	 if (staffRepository.existsByEmail(staffDto.getEmail())) {
		            throw new ResourceNotFoundException("Staff", "email", staffDto.getEmail());
		        }
	    	// Chuyển đổi từ DTO sang entity
	        Staff staff = modelMapper.map(staffDto, Staff.class);

	        // Tìm vai trò dựa trên roleId và gán cho staff
	        Role role = roleRepository.findById(staffDto.getRoleId())
	                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", staffDto.getRoleId()));
	        staff.setRole(role);

	        // Bỏ qua mã hóa mật khẩu và lưu mật khẩu thô
	        if (staffDto.getPassword() != null) { // Kiểm tra xem mật khẩu có hợp lệ không
	            staff.setPassword(staffDto.getPassword()); // Lưu mật khẩu thô
	        }
	        
	        // Lưu staff vào cơ sở dữ liệu
	        Staff savedStaff = staffRepository.save(staff);
	        // Chuyển đổi từ entity sang DTO và trả về
	        return modelMapper.map(savedStaff, StaffDto.class);
	    }
	    
	    @Override
	    public StaffDto updateStaff(StaffDto staffDto, String email) {
	        // Sửa đổi phương thức tìm kiếm để sử dụng email
	        Staff existingStaff = staffRepository.findByEmail(email)  // Thay đổi ở đây
	                .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", email));

	        Role role = roleRepository.findById(staffDto.getRoleId())
	                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", staffDto.getRoleId()));
	        existingStaff.setRole(role);
	        
	        // Cập nhật thông tin nhân viên
	        existingStaff.setName(staffDto.getName());
	        existingStaff.setPhone(staffDto.getPhone());
	        existingStaff.setGender(staffDto.getGender());
	        existingStaff.setAge(staffDto.getAge());
	        
	        Staff updatedStaff = staffRepository.save(existingStaff);
	        return modelMapper.map(updatedStaff, StaffDto.class);
	    }

	    @Override
	    public void deleteStaff(String email) {
	        Staff staff = staffRepository.findByEmail(email)  // Thay đổi ở đây
	                .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", email));
	        staffRepository.delete(staff);
	    }

//	    @Override
//	    public StaffDto getStaffByEmail(String email) {
//	        Staff staff = staffRepository.findByEmail(email)  // Thay đổi ở đây
//	                .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", email));
//	        return modelMapper.map(staff, StaffDto.class);
//	    }

	    @Override
	    public StaffDto getStaffByEmail(String email) {
	        Staff staff = staffRepository.findByEmail(email).orElse(null);
	        return staff != null ? modelMapper.map(staff, StaffDto.class) : null;
	    }
	
	    @Override
	    public List<StaffDto> getAllStaffs() {
	        return staffRepository.findAll().stream()
	                .map(staff -> modelMapper.map(staff, StaffDto.class))
	                .collect(Collectors.toList());
	    }

}
