package com.example.testbot.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class QuestionController {

    @GetMapping
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("question");
        return modelAndView;
    }
}