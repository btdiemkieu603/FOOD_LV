package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.RoleDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.RoleService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@PostMapping("/add")
	public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto) {
		RoleDto createRole = this.roleService.createRole(roleDto);
		return new ResponseEntity<RoleDto>(createRole, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{roleId}")
	public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer roleId) {
		this.roleService.deleteRole(roleId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Role is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@GetMapping("/")
	public ResponseEntity<List<RoleDto>> getAllRoles() {
		List<RoleDto> roles = this.roleService.getRoles();
		return ResponseEntity.ok(roles);
	}
}
