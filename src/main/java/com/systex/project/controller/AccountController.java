package com.systex.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.systex.project.model.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/")
	public String index() {
		return "index";

	}

	@GetMapping("/home")
	public String home() {
		return "home";

	}

	@GetMapping("/newlogin")
	public String newlogin() {
		return "newlogin";

	}

	@PostMapping("/login")
	public String login(@ModelAttribute("account") String account, Model model, HttpServletRequest request) {
		request.getSession().setAttribute("account", accountRepository.findByAccount(account));
		return "home";
	}

	@PostMapping("/newlogin")
	public ResponseEntity<Map<String, Object>> newlogin(HttpServletRequest request) {
		String account = (String) request.getAttribute("account");
		request.getSession().setAttribute("account", accountRepository.findByAccount(account));
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		result.put("requestPath", "/project/home");
		return ResponseEntity.ok(result);
	}

}
