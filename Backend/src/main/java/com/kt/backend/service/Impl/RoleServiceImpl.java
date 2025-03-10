package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.RoleDto;
import com.kt.backend.entity.Role;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.RoleRepository;
import com.kt.backend.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = this.modelMapper.map(roleDto, Role.class);
		Role addRole = this.roleRepository.save(role);
		return this.modelMapper.map(addRole, RoleDto.class);
	}

	@Override
	public void deleteRole(Integer roleId) {
		Role role = this.roleRepository.findById(roleId)
					.orElseThrow(()-> new ResourceNotFoundException("Role ","RoleId", roleId));
		this.roleRepository.delete(role);
	}

	@Override
	public List<RoleDto> getRoles() {
		List<Role> roles = this.roleRepository.findAll();
		List<RoleDto> roleDtos = roles.stream().map((role)-> this.modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());	
		return roleDtos;
	}

}
