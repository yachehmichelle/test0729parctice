package com.systex.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.systex.project.model.Account;
import com.systex.project.model.AccountRepository;
import com.systex.project.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepository;
	
	
	@GetMapping("/index")
	public String home(Model model) {
		return "index";
	}
	

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    return "register";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("account") String account, @ModelAttribute("password") String password,
			Model model) {
		if ((account.isEmpty()) || (password.isEmpty())) {
			model.addAttribute("error", "輸入不可為空白");
			model.addAttribute("account","");
			model.addAttribute("password","");
			return "index";
		}
		if (accountService.checkAccoumt(account, password)) {
			model.addAttribute("account", accountRepository.findByAccount(account));
			return "home";
		}else {
			model.addAttribute("account","");
			model.addAttribute("password","");
			model.addAttribute("error", "密碼錯誤");
			return "index";
		}
	}
		
		@PostMapping("/register")
		public String register(@ModelAttribute Account account ,Model model) {
				accountRepository.save(account);
				return "index";
			}

	

}
