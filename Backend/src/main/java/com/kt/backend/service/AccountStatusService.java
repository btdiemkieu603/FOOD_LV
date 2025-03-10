package com.kt.backend.service;

import java.util.List;
import java.util.Optional;

import com.kt.backend.dto.AccountStatusDto;
import com.kt.backend.entity.AccountStatus;

public interface AccountStatusService {

	AccountStatusDto createAccountStatus(AccountStatusDto acStatusDto);
	
	void deleteAccountStatus(Integer acStatusId);	
	
	List<AccountStatusDto> getAccountStatuses();
	
	Optional<AccountStatus>  findAccountStatus(String status);
}
