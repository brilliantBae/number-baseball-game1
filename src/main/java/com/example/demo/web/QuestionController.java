package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "qna/form";
	}
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = new Question(loginUser.getUserId(), title, contents);
		questionRepository.save(question);
		return "redirect:/";
	}
	
	@GetMapping("/form/{id}")
	public String showDetails(@PathVariable Long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "qna/show";
	}
}
