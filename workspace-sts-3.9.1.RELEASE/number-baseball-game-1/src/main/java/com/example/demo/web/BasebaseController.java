package com.example.demo.web;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Baseball;

@Controller
public class BasebaseController {
	// 브라우저에서 웹서버에 요청을 보낼때 get, post 방식.
    ArrayList<Integer> computerBalls = Baseball.generateComputerBalls();
	@GetMapping("/game")
	public String getThreeNums() {
		return "game";
	}
	@PostMapping("/result")
	public String showResult(String numbers, Model model) {
		int strike = 0;
		int ball = 0;
        ArrayList<Integer> userBalls = Baseball.inputUserBalls(numbers);
        for (int i = 0; i < userBalls.size(); i++) {
            int result = Baseball.calculateBall(computerBalls, userBalls.get(i), i);
            if (result == 2) {
                strike++;
            } else if (result == 1) {
                ball++;
            }
        }
        model.addAttribute("strike", strike);
        model.addAttribute("ball", ball);
		return "result";
	}
}
