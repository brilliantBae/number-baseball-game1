package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;

@Controller
public class QuestionController {
	@Autowired
	QuestionRepository questionRepository;
	
	List<Question> questions = new ArrayList<>();
	@GetMapping("/qna")
	public String show() {
		return "qna/form";
	}
	@GetMapping("/")
	public String showQuestions(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
	@PostMapping("/questions")
	public String ask(Question question) {
		questionRepository.save(question);
		return "redirect:/";
	}
	@GetMapping("/qna/{id}")
	public String showDetails(@PathVariable Long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "qna/show";
	}
}
