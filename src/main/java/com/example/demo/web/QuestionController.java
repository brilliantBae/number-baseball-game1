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

import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
	
	@GetMapping("/{id}")
	public String showDetails(@PathVariable Long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
		if(!HttpSessionUtils.isLoginUser(session)) {// 미로그인 시.
			logger.debug("Not logined yet");
			return "/users/loginForm";
		}
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameWriter(loginedUser.getUserId())) {
			logger.debug("Not same user");
			throw new IllegalStateException("You can not modify other users' question!");
		}
		model.addAttribute("question", question);
		return "qna/updateForm";
	}
	
	@PutMapping("/{id}/update")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {// 미로그인 시.
			return "/users/loginForm";
		}
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameWriter(loginedUser.getUserId())) {
			throw new IllegalStateException("You can not modify other users' question!");
		}
		question.update(title, contents);
		questionRepository.save(question);
		return "redirect:/";
	}
//	@PostMapping("{id}/remove")
//	public String remove() {
//		return "redirect:/";
//	}
}
