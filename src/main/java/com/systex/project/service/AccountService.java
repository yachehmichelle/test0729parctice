package com.systex.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systex.project.model.Account;
import com.systex.project.model.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	public boolean checkAccoumt(String account, String password) {
		Optional<Account> optional = accountRepository.findByAccount(account);
		if (optional.isPresent()) {
			Account account1 = optional.get();
			if (account1.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

}
