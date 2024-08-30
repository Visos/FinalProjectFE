package com.betacom.fe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexProvaController {
	
	@GetMapping(value = {"/jpa", "/index"})
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}


}
