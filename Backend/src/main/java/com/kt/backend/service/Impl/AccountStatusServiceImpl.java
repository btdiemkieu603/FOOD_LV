package com.kt.backend.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.AccountStatusDto;
import com.kt.backend.entity.AccountStatus;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.AccountStatusRepository;
import com.kt.backend.service.AccountStatusService;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {

	@Autowired
	private AccountStatusRepository acStatusRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AccountStatusDto createAccountStatus(AccountStatusDto acStatusDto) {
		AccountStatus acStatus = this.modelMapper.map(acStatusDto, AccountStatus.class);
		AccountStatus addAcStatus = this.acStatusRepository.save(acStatus);
		return this.modelMapper.map(addAcStatus, AccountStatusDto.class);
	}

	@Override
	public void deleteAccountStatus(Integer acStatusId) {
		AccountStatus acStatus = this.acStatusRepository.findById(acStatusId)
				.orElseThrow(() -> new ResourceNotFoundException("AccountStatus ", "AccountStatusId", acStatusId));
		this.acStatusRepository.delete(acStatus);
	}

	@Override
	public List<AccountStatusDto> getAccountStatuses() {
		List<AccountStatus> accountStatuses = this.acStatusRepository.findAll();
		List<AccountStatusDto> accountStatusDtos = accountStatuses.stream().map((accountStatus)-> this.modelMapper.map(accountStatus, AccountStatusDto.class)).collect(Collectors.toList());	
		return accountStatusDtos;
	}

	@Override
	public Optional<AccountStatus> findAccountStatus(String status) {
		AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus(status);
		return Optional.ofNullable(acStatus);
	}

}
