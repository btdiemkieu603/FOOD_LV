package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.RoleDto;

public interface RoleService {

	RoleDto createRole(RoleDto roleDto);
	
	void deleteRole(Integer roleId);	
	
	List<RoleDto> getRoles();
}
