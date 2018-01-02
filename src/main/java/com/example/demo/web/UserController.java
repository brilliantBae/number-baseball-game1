package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			logger.debug("아이디 일치하지 않음");
			return "redirect:/users/loginForm";
		}
		if(!user.matchPassword(password)) {
			logger.debug("password 일치하지 않음");
			return "redirect:/users/loginForm";
		}
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	@GetMapping("/signUp")
	public String show() {
		return "user/form";
	}
	
	@GetMapping("")
	public String showList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}
	
	@PostMapping("/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/{id}")
	public String showProfile(@PathVariable Long id, Model model) {
		for(User user : userRepository.findAll()) {
			if(user.matchId(id)) {
				model.addAttribute("user", userRepository.findOne(id));
			}
		}
		return "user/profile";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can only modify your own info.");
		}
		model.addAttribute("user", userRepository.findOne(id));
		return "user/updateForm";
	}
	@PutMapping("/{id}")
	public String modify(@PathVariable Long id, Model model, User updatedUser, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can only modify your own info.");
		}
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		model.addAttribute("user", user);
		return "redirect:/users";
	}
	
}
