package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/signUp")
	public String show() {
		return "user/form";
	}
	
	@GetMapping("/users")
	public String showList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}
	
	@PostMapping("/user/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/user/{id}")
	public String showProfile(@PathVariable Long id, Model model) {
		for(User user : userRepository.findAll()) {
			if(user.getId().equals(id)) {
				model.addAttribute("user", userRepository.findOne(id));
			}
		}
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.findOne(id));
		return "user/updateForm";
	}
	@PutMapping("/user/{id}")
	public String modify(@PathVariable Long id, Model model, User newUser) {
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		model.addAttribute("user", user);
		return "redirect:/users";
	}
	
}
