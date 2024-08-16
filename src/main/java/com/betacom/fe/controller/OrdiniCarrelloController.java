package com.betacom.fe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrdiniCarrelloController {
	
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(OrdiniCarrelloController.class);
	
	@GetMapping("/carrello")
	public ModelAndView carrello() {
		ModelAndView mav = new ModelAndView("carrello");		
		
		return mav;
	}
	
	@GetMapping("/ordini")
	public ModelAndView ordini() {
		ModelAndView mav = new ModelAndView("ordini");		
		
		return mav;
	}

}
