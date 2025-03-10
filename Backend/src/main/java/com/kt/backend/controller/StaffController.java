package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.dto.StaffDto;
import com.kt.backend.dto.UpdateAccountDto;

import com.kt.backend.dto.AddressDto;
import com.kt.backend.dto.ChangePasswordDto;
import com.kt.backend.response.ApiResponse;

import com.kt.backend.service.StaffService;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/staff")



public class StaffController {

	@Autowired
    private StaffService staffService;

    @PostMapping("/add")
    public ResponseEntity<StaffDto> createStaff(@RequestBody StaffDto staffDto) {
        StaffDto createdStaff = staffService.createStaff(staffDto);
        return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);
    }

    @PutMapping("/{staffEmail}")
    public ResponseEntity<StaffDto> updateStaff(@RequestBody StaffDto staffDto, @PathVariable String staffEmail) {
        StaffDto updatedStaff = staffService.updateStaff(staffDto, staffEmail);
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/{staffEmail}")
    public ResponseEntity<ApiResponse> deleteStaff(@PathVariable String staffEmail) {
        staffService.deleteStaff(staffEmail);
        return new ResponseEntity<>(new ApiResponse("Staff deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/{staffEmail}")
    public ResponseEntity<StaffDto> getStaffByEmail(@PathVariable String staffEmail) {
        StaffDto staff = staffService.getStaffByEmail(staffEmail);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StaffDto>> getAllStaffs() {
        List<StaffDto> staffs = staffService.getAllStaffs();
        return ResponseEntity.ok(staffs);
    }
}
