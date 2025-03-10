package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.dto.UpdateAccountDto;
import com.kt.backend.dto.CartDto;
import com.kt.backend.dto.ChangePasswordDto;
import com.kt.backend.dto.LoginDto;
import com.kt.backend.dto.AccountDto;
import com.kt.backend.dto.AddressDto;
import com.kt.backend.entity.Account;
import com.kt.backend.entity.AccountStatus;
import com.kt.backend.entity.Address;
import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Role;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.AccountRepository;
import com.kt.backend.repository.AccountStatusRepository;
import com.kt.backend.repository.RoleRepository;
import com.kt.backend.service.AccountService;
import com.kt.backend.service.AddressService;
import com.kt.backend.service.CartService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AccountStatusRepository acStatusRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResAccountDto createCustomerAccount(AccountDto accountDto, Integer roleId) {		
		Role role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role ", "roleId", roleId));
		String phoneNumber = accountDto.getPhonenumber();
		String email = accountDto.getEmail();
		List<Account> listAc = this.accountRepository.isValidOfPhoneNumberAndEmail(phoneNumber, email);
		if(listAc.isEmpty()) {
		CartDto cartDto = new CartDto();
		cartDto.setDetail("Cart of "+accountDto.getLastname());
		CartDto newCart = this.cartService.createCart(cartDto);
		Cart cart = this.modelMapper.map(newCart, Cart.class);	
		AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("Active");	
		Account account = this.modelMapper.map(accountDto, Account.class);
		account.setRole(role);
		account.setCart(cart);
		account.setAccount_status(acStatus);
		Account newAccount = this.accountRepository.save(account);
		return this.modelMapper.map(newAccount, ResAccountDto.class);
		}else {
			return new ResAccountDto();
		}
	}

	@Override
	public ResAccountDto createStaffAccount(AccountDto accountDto, Integer roleId) {		
		Role role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role ", "roleId", roleId));
		AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("Active");	
		String phoneNumber = accountDto.getPhonenumber();
		String email = accountDto.getEmail();
		List<Account> listAc = this.accountRepository.isValidOfPhoneNumberAndEmail(phoneNumber, email);
		if(listAc.isEmpty()) {
			Account account = this.modelMapper.map(accountDto, Account.class);		
			account.setRole(role);
			account.setAccount_status(acStatus);	
			Account newAccount = this.accountRepository.save(account);
			return this.modelMapper.map(newAccount, ResAccountDto.class);
		}else {
			return new ResAccountDto();
		}
		
	}

	@Override
	public void deleteAccount(Integer accountId) {
		
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		if(account.getCart() == null) {
			this.accountRepository.delete(account);
		}else {
			Integer cartId = account.getCart().getId();
			this.accountRepository.delete(account);
			this.cartService.deleteCart(cartId);
		}		
	}
	
	@Override
	public List<ResAccountDto> getAccountsByRole(Integer roleId) {
		Role role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role ", "roleId", roleId));
		List<Account> accounts = this.accountRepository.findByRole(role);
		List<ResAccountDto> accountDtos = accounts.stream().map((account) -> this.modelMapper.map(account, ResAccountDto.class))
				.collect(Collectors.toList());

		return accountDtos;
	}
	
//	@Override
//	public List<ResAccountDto> getAccountsByStatus(Integer statusId) {
//		Role role = this.roleRepository.findById(statusId)
//				.orElseThrow(() -> new ResourceNotFoundException("Role ", "statusId", statusId));
//		List<Account> accounts = this.accountRepository.findByRole(role);
//		List<ResAccountDto> accountDtos = accounts.stream().map((account) -> this.modelMapper.map(account, ResAccountDto.class))
//				.collect(Collectors.toList());
//
//		return accountDtos;
//	}

	@Override
	public ResAccountDto blockAccount(Integer accountId) {		
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		
		AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("Blocked");
		account.setAccount_status(acStatus);
		Account updatedAccount = this.accountRepository.save(account);
		return this.modelMapper.map(updatedAccount, ResAccountDto.class);
	}

	@Override
	public ResAccountDto openAccount(Integer accountId) {
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		
		AccountStatus acStatus = this.acStatusRepository.findAccountStatusByStatus("Active");
		account.setAccount_status(acStatus);
		Account updatedAccount = this.accountRepository.save(account);
		return this.modelMapper.map(updatedAccount, ResAccountDto.class);
	}

	@Override
	public ResAccountDto updateAddressForAccount(Integer accountId, AddressDto addressDto) {
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		
		if(account.getAddress() != null) {
			Integer addressIdCurrent = account.getAddress().getId();
			AddressDto newAddressDto = this.addressService.createAddress(addressDto);
			Address newAddress = this.modelMapper.map(newAddressDto, Address.class);
			account.setAddress(newAddress);
			Account updatedAccount = this.accountRepository.save(account);
			this.addressService.deleteAddress(addressIdCurrent);		
			return this.modelMapper.map(updatedAccount, ResAccountDto.class);
			
		}else {
			AddressDto newAddressDto = this.addressService.createAddress(addressDto);
			Address newAddress = this.modelMapper.map(newAddressDto, Address.class);	
			account.setAddress(newAddress);
			Account updatedAccount = this.accountRepository.save(account);
			return this.modelMapper.map(updatedAccount, ResAccountDto.class);
			
		}

	}

	@Override
	public ResAccountDto getAccount(Integer accountId) {
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		return this.modelMapper.map(account, ResAccountDto.class);
	}

	@Override
	public ResAccountDto login(LoginDto loginDto) {
		Account account = this.accountRepository.findAccountByLoginDto(loginDto.getPhonenumber(), loginDto.getPassword());
		if(account == null) {
			return new ResAccountDto();
		}else {
			return this.modelMapper.map(account, ResAccountDto.class);
		}
		
	}
	
//	@Override
//	public ResAccountDto loginAdmin(LoginDto loginDto) {
//		Account account = this.accountRepository.findAccountByLoginDto(loginDto.getPhonenumber(), loginDto.getPassword());
//		if(account == null) {
//			return new ResAccountDto();
//		}else {
//			return this.modelMapper.map(account, ResAccountDto.class);
//		}
//		
//	}

	@Override
	public ResAccountDto updateAccount(UpdateAccountDto updateAccountDto, Integer accountId) {
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		account.setFirstname(updateAccountDto.getFirstname());
		account.setLastname(updateAccountDto.getLastname());
		account.setPhonenumber(updateAccountDto.getPhonenumber());
		account.setEmail(updateAccountDto.getEmail());
		Account updatedAccount = this.accountRepository.save(account);
		return this.modelMapper.map(updatedAccount, ResAccountDto.class);
	}

	@Override
	public boolean changePassword(Integer accountId, ChangePasswordDto changePasswordDto) {
		Account account = this.accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountId", accountId));
		if(changePasswordDto.getPassword().equals(account.getPassword())) {
			account.setPassword(changePasswordDto.getNewpassword());
			this.accountRepository.save(account);
			return true;
		}else {
			return false;
		}
	}

}
