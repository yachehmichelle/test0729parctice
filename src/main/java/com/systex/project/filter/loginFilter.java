package com.systex.project.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.project.model.Account;
import com.systex.project.service.AccountService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class loginFilter extends OncePerRequestFilter {
	@Autowired
	AccountService accountService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestPath = request.getRequestURI();
		if (requestPath.endsWith(".css")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (requestPath.contains("/logout")) {// 登出後刪除所有session
		request.getSession().invalidate();
		response.setContentType("application/json"); //設定回傳格式
        response.setCharacterEncoding("UTF-8");//設定回傳格式
		Map<String, Object> result = new HashMap<>();
		result.put("result","success");
		result.put("requestPath","/project");//回給前端ajax訊息
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
		
		return;
	}

		if (requestPath.contains("/login")) {// login走這裡
			String account = request.getParameter("account");
			String password = request.getParameter("password");

			if (account == null || password == null) {// 未登入 ->導入登入頁面
				if (request.getSession().getAttribute("error") == null) {
					request.getSession().removeAttribute("error");// 進入登入頁面不要看到error訊息
				}
				filterChain.doFilter(request, response);
				return;
			}
			if (!accountService.checkAccoumt(account, password)) {// 如果帳號或是密碼不正確
				request.getSession().setAttribute("error", "帳號或是密碼錯誤!");
				response.sendRedirect("/project");// 原始登入頁面
				return;
			}
		}

		if (requestPath.contains("/newlogin") & request.getMethod().equals("POST")) {// newlogin走這裡
			String json = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));
			// 讀取到數據內容{"account":"testAAA","password":"FDSFSDF"}
           
		
			ObjectMapper objectmapper=new ObjectMapper();
			Account inputaccount =objectmapper.readValue(json, Account.class);
			
			// 抓出account和password
			String account = inputaccount.getAccount();
			String password = inputaccount.getPassword();
			request.setAttribute("account", account);
		    request.setAttribute("password", password);

			if (account == null || password == null) {// 未登入 ->導入登入頁面
				if (request.getSession().getAttribute("error") == null) {
					request.getSession().removeAttribute("error");// 進入登入頁面不要看到error訊息
				}
				filterChain.doFilter(request, response);
				return;
			}
			if (!accountService.checkAccoumt(account, password)) {// 如果帳號或是密碼不正確
		        response.setContentType("application/json"); //設定回傳格式
		        response.setCharacterEncoding("UTF-8");//設定回傳格式
				Map<String, Object> result = new HashMap<>();
				result.put("result","error");
				result.put("error","帳號或密碼錯誤!");//回給前端ajax訊息
		        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
				return;

			}
		}
		

		filterChain.doFilter(request, response);
	}

}
